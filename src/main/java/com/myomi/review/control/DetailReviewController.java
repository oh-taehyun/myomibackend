package com.myomi.review.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myomi.control.Controller;
import com.myomi.exception.FindException;
import com.myomi.review.service.ReviewService;


public class DetailReviewController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		ObjectMapper mapper = new ObjectMapper();
		ReviewService service = new ReviewService();
		int num = Integer.parseInt(request.getParameter("num"));
		try {
			Map<String, Object> review = service.FindOneReview(num);
			String JsonStr = mapper.writeValueAsString(review);
			return JsonStr;

		} catch (FindException e) {
			e.printStackTrace();

			Map<String, String> map = new HashMap<>();
			map.put("msg", e.getMessage());
			String jsonStr = mapper.writeValueAsString(map);
			return jsonStr;
		}
	
	}

}