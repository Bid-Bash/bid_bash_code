package com.csuf.bidbash.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.csuf.bidbash.pojos.AuctionSale;
import com.csuf.bidbash.repos.AuctionSaleRepo;
import com.csuf.bidbash.repos.BidRequestRepo;
import com.csuf.bidbash.utils.EmailUtils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
public class AuctionSaleService {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private AuctionSaleRepo saleRepo;

	@Autowired
	private BidRequestRepo bidRequestRepo;

	@Value("${spring.dev.tempmail}")
	private String tempMail;

	public AuctionSale sale(AuctionSale sale) {
		sale.setTime(LocalDateTime.now());
		return saleRepo.save(sale);
	}

	public int getNextSaleId() {
		return saleRepo.nextAuctionSaleId().orElse(0) + 1;
	}

	public List<Object> getSaleTransactionForUser(int userId) {
		return saleRepo.getUserSaleOrders(userId);
	}

	@Transactional
	public void updateTransactionId(int auctionRequestId, String transactionId) {
		saleRepo.findByAuctionRequestId(auctionRequestId, transactionId);
	}

	public void sendSimpleEmail(int auctionRequestId) {
		Object[] list = (Object[]) bidRequestRepo.getDetailsforEmail(auctionRequestId);
		String cName = (String) list[0];
		double orderVal = (double) list[1];

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		String emailTemplate = EmailUtils.ORDER_CONFIRMATION.getEmailTemplate();
		String email2 = String.format(emailTemplate, cName, orderVal);

		try {
			helper.setTo(tempMail);
			helper.setSubject("Order Placed");
			helper.setText(email2, true);
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}

		emailSender.send(message);
	}

}
