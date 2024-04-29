package com.csuf.bidbash.utils;

public enum EmailUtils {
	
	ORDER_CONFIRMATION("<!DOCTYPE html>\r\n"
				+ "<html lang=\"en\">\r\n"
				+ "<head>\r\n"
				+ "    <meta charset=\"UTF-8\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "    <style>\r\n"
				+ "        body {\r\n"
				+ "            font-family: Arial, sans-serif;\r\n"
				+ "            margin: 0;\r\n"
				+ "            padding: 0;\r\n"
				+ "            background-color: #f4f4f4;\r\n"
				+ "        }\r\n"
				+ "        .container {\r\n"
				+ "            max-width: 600px;\r\n"
				+ "            margin: 20px auto;\r\n"
				+ "            padding: 20px;\r\n"
				+ "            background-color: #ffffff;\r\n"
				+ "            border-radius: 5px;\r\n"
				+ "            box-shadow: 0 0 10px rgba(0,0,0,0.1);\r\n"
				+ "        }\r\n"
				+ "        .logo {\r\n"
				+ "            text-align: center;\r\n"
				+ "        }\r\n"
				+ "        .logo img {\r\n"
				+ "            max-width: 150px;\r\n"
				+ "            height: auto;\r\n"
				+ "        }\r\n"
				+ "        .title {\r\n"
				+ "            text-align: center;\r\n"
				+ "            margin-top: 20px;\r\n"
				+ "        }\r\n"
				+ "        .message {\r\n"
				+ "            margin-top: 20px;\r\n"
				+ "        }\r\n"
				+ "    </style>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "    <div class=\"container\">\r\n"
				+ "        <div class=\"logo\">\r\n"
				+ "            <img src=\"https://bidbashstorage.blob.core.windows.net/bidbash-container/logo-black.png\" alt=\"Company Logo\">\r\n"
				+ "        </div>\r\n"
				+ "        <h2 class=\"title\">Order Confirmation</h2>\r\n"
				+ "        <p class=\"message\">Dear %s,</p>\r\n"
				+ "        <p class=\"message\">We are delighted to inform you that your order has been successfully placed.</p>\r\n"
				+ "        <!-- You can include order details here if needed -->\r\n"
				+ "        <p>Total Order Value: <span style=\"font-weight: bold;\">$%.2f</span> </p>\r\n"
				+ "        <p class=\"message\">Thank you for choosing us. If you have any questions or concerns, feel free to <a href=\"mailto:noreplaybidbash@gmail.com\">contact us</a>.</p>\r\n"
				+ "        <p class=\"message\">Best Regards,<br>BidBash</p>\r\n"
				+ "    </div>\r\n"
				+ "</body>\r\n"
				+ "</html>");
	
	private String emailTemplate;
	
	EmailUtils(String template){
		this.emailTemplate = template;
	}
	
	public String getEmailTemplate() {
		return this.emailTemplate;
	}
	
	

}
