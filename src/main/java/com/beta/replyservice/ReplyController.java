package com.beta.replyservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class ReplyController {

	@GetMapping("/reply")
	public ReplyMessage replying() {
		return new ReplyMessage("Message is empty");
	}

	public static boolean validRule(String rule){
		if(rule.length() != 2)
			return false;
		int rule1 = rule.charAt(0) - '0';
		if((rule1 > 2) || (rule1 < 1))
			return false;
		int rule2 = rule.charAt(1) - '0';
        return (rule2 <= 2) && (rule2 >= 1);
    }
	public static String getMd5(String msg)
	{
		try {

			MessageDigest md = MessageDigest.getInstance("MD5");

			byte[] messageDigest = md.digest(msg.getBytes());

			BigInteger no = new BigInteger(1, messageDigest);

			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}

		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getReverse(String msg){
		StringBuilder input = new StringBuilder();
		input.append(msg);
		input.reverse();
		return input.toString();
	}
	public static String applyRule(String msg, char rule)
	{
		if(rule == '1')
			return getReverse(msg);
		else
			return getMd5(msg);
	}
	@GetMapping("/reply/{message}")
	public ReplyMessage replying(@PathVariable String message) {
		return new ReplyMessage(message);
	}

	@GetMapping("v2/reply/{message}")
	public ResponseEntity<ReplyMessage> replyingV2(@PathVariable String message) {
		String[] msgArr = message.split("-");
		String rule = msgArr[0];
		String msg = msgArr[1];
		if(validRule(rule))
		{
			String ruleOneApplied = applyRule(msg,rule.charAt(0));
			String ruleTwoApplied = applyRule(ruleOneApplied,rule.charAt(1));
			return new ResponseEntity<ReplyMessage>(new ReplyMessage(ruleTwoApplied), HttpStatus.OK);
		}
		else
			return new ResponseEntity<ReplyMessage>(new ReplyMessage("Invalid input"), HttpStatus.BAD_REQUEST);
	}
}