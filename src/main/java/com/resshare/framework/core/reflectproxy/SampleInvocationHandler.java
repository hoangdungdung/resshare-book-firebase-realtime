package com.resshare.framework.core.reflectproxy;
import com.resshare.framework.core.reflectproxy.AnoMethod;
import com.resshare.framework.core.reflectproxy.AnoModule;
import com.resshare.framework.model.Input;
import com.resshare.springboot.StartServiceListenerCore;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class SampleInvocationHandler implements InvocationHandler {
	public static void main(String[] args) {



		String request_data= "{\"application\":\"book\",\"data\":{\"layout\":{\"widget\":\"android.widget.LinearLayout\",\"properties\":{\"0\":{\"layout_width\":\"match_parent\"},\"1\":{\"background\":\"@drawable/bg_white_selector\"},\"2\":{\"orientation\":\"vertical\"},\"3\":{\"layout_height\":\"380dp\"}},\"views\":{\"0\":{\"widget\":\"android.widget.TextView\",\"properties\":{\"0\":{\"layout_width\":\"300dp\"},\"1\":{\"background\":\"@drawable/bg_white_selector\"},\"2\":{\"id\":\"@+id/txtDescription\"},\"3\":{\"layout_height\":\"45dp\"},\"4\":{\"textSize\":\"16sp\"},\"5\":{\"text\":\"Các chức năng ứng dụng mượn sách\"},\"6\":{\"ellipsize\":\"end\"},\"7\":{\"maxLines\":2},\"8\":{\"textColor\":\"@color/browser_actions_text_color\"},\"9\":{\"layout_height:\":\"30dp\"}}},\"1\":{\"widget\":\"GridView\",\"properties\":{\"0\":{\"numColumns\":3},\"1\":{\"id\":\"@+id/dashboardGridView\"},\"2\":{\"grid_view_layout_item\":{\"widget\":\"android.widget.LinearLayout\",\"properties\":{\"0\":{\"layout_width\":\"120dp\"},\"1\":{\"layout_height\":\"180dp\"},\"2\":{\"padding\":\"5dp\"},\"3\":{\"orientation\":\"vertical\"}},\"views\":{\"0\":{\"widget\":\"android.widget.ImageView\",\"properties\":{\"0\":{\"layout_width\":\"60dp\"},\"1\":{\"id\":\"@+id/iconItem\"},\"2\":{\"layout_height\":\"60dp\"},\"3\":{\"src\":\"@drawable/logo\"},\"4\":{\"layout_marginRight\":\"10dp\"},\"5\":{\"field_name\":\"icon\"}}},\"1\":{\"widget\":\"android.widget.TextView\",\"properties\":{\"0\":{\"layout_width\":\"120dp\"},\"1\":{\"id\":\"@+id/txtDescriptionItem\"},\"2\":{\"layout_height\":\"80dp\"},\"3\":{\"text\":\"Hi all\"},\"4\":{\"textSize\":\"15sp\"},\"5\":{\"layout_marginTop\":\"0dp\"},\"6\":{\"field_name\":\"description\"},\"7\":{\"maxLines\":3}}}}}},\"3\":{\"grid_view_data\":{\"list_item\":{\"0\":{\"flow_chart\":\"\",\"icon\":\"https://firebasestorage.googleapis.com/v0/b/covid19-b58d6.appspot.com/o/icon%2Fgrid_icon%2Fabout48.png?alt=media&token=b2007d15-75f8-4c98-878d-62e63979eec4\",\"description\":\"Giới thiệu ứng dụng\",\"layout_form\":\"../configuration/system_setting/layout/android/form/introduction\"},\"1\":{\"fragment\":\"com.resshare.core.screen.DynamicFragmentRecyclerView\",\"flow_chart\":\"\",\"icon\":\"https://firebasestorage.googleapis.com/v0/b/book-e0228.appspot.com/o/icon_main%2Ficons8-search-48.png?alt=media&token=3e1c8522-5eb4-4432-9b7f-529ad2733553\",\"description\":\"Tìm kiếm\",\"layout_form\":\"../configuration/system_setting/layout/android/form/search\"},\"2\":{\"flow_chart\":\"\",\"icon\":\"https://firebasestorage.googleapis.com/v0/b/book-e0228.appspot.com/o/icon_main%2Ficons8-add-book-48.png?alt=media&token=95e59da8-6ed8-4b89-9340-8ddbb99a45c0\",\"description\":\"Thêm sách\",\"layout_form\":\"../configuration/system_setting/layout/android/form/add_book\"},\"3\":{\"fragment\":\"com.resshare.core.screen.LocationDynamicActivity\",\"flow_chart\":\"\",\"icon\":\"https://firebasestorage.googleapis.com/v0/b/book-e0228.appspot.com/o/icon_main%2Ficons8-contact-50.png?alt=media&token=1080ac13-4f91-4d79-8562-cfe1e9e1f872\",\"description\":\"Thông tin liên hệ\",\"layout_form\":\"../configuration/system_setting/layout/android/form/toicobinhoxy\"},\"4\":{\"fragment\":\"com.resshare.core.screen.DynamicFragmentRecyclerView\",\"flow_chart\":\"\",\"icon\":\"https://firebasestorage.googleapis.com/v0/b/book-e0228.appspot.com/o/icon_main%2Ficons8-bookcase-64.png?alt=media&token=b52cc930-9f43-4a9c-9c55-b66292a182b2\",\"description\":\"Tủ sách của bạn\",\"layout_form\":\"../configuration/system_setting/layout/android/form/my_bookcase\"}}}},\"4\":{\"layout_height\":\"370dp\"}}}}},\"script_param\":{\"grid_view_layout_item\":{\"widget\":\"android.widget.LinearLayout\",\"properties\":[{\"layout_width\":\"wrap_content\"},{\"layout_height\":\"180dp\"},{\"padding\":\"5dp\"},{\"orientation\":\"vertical\"}],\"views\":[{\"widget\":\"android.widget.ImageView\",\"action\":{\"post_conlection\":\"../openform\",\"field_name_post\":\"examination_key,questions_name_master,questions_name_detail\",\"command\":\"open_form\"},\"properties\":[{\"id\":\"@+id/iconItem\"},{\"layout_width\":\"50dp\"},{\"layout_height\":\"50dp\"},{\"src\":\"@drawable/logo\"},{\"layout_marginRight\":\"10dp\"},{\"field_name\":\"icon\"}]},{\"widget\":\"android.widget.TextView\",\"action\":{\"post_conlection\":\"../openform\",\"field_name_post\":\"examination_key,questions_name_master,questions_name_detail\",\"command\":\"open_form\"},\"properties\":[{\"id\":\"@+id/txtDescriptionItem\"},{\"layout_width\":\"wrap_content\"},{\"layout_height\":\"80dp\"},{\"text\":\"Hi all\"},{\"textSize\":\"15sp\"},{\"layout_marginTop\":\"5dp\"},{\"field_name\":\"description\"}]},{\"widget\":\"android.widget.TextView\",\"properties\":[{\"id\":\"@+id/layout_form\"},{\"layout_width\":\"0dp\"},{\"layout_height\":\"0dp\"},{\"field_name\":\"layout_form\"}]},{\"widget\":\"android.widget.TextView\",\"properties\":[{\"id\":\"@+id/layout_form_ios\"},{\"layout_width\":\"0dp\"},{\"layout_height\":\"0dp\"},{\"field_name\":\"layout_form_ios\"}]},{\"widget\":\"android.widget.TextView\",\"properties\":[{\"layout_width\":\"0dp\"},{\"id\":\"@+id/fragment\"},{\"layout_height\":\"0dp\"},{\"field_name\":\"fragment\"}]},{\"widget\":\"android.widget.TextView\",\"properties\":[{\"layout_width\":\"0dp\"},{\"id\":\"@+id/flow_chart\"},{\"layout_height\":\"0dp\"},{\"field_name\":\"flow_chart\"}]},{\"widget\":\"android.widget.TextView\",\"properties\":[{\"layout_width\":\"0dp\"},{\"id\":\"@+id/screen_name\"},{\"layout_height\":\"0dp\"},{\"field_name\":\"screen_name\"}]}]},\"grid_view_data\":[{\"flow_chart\":\"\",\"icon\":\"https://firebasestorage.googleapis.com/v0/b/covid19-b58d6.appspot.com/o/icon%2Fgrid_icon%2Fabout48.png?alt=media&token=b2007d15-75f8-4c98-878d-62e63979eec4\",\"description\":\"Giới thiệu ứng dụng\",\"layout_form\":\"../configuration/system_setting/layout/android/form/introduction\"},{\"fragment\":\"com.resshare.core.screen.DynamicFragmentRecyclerView\",\"flow_chart\":\"\",\"icon\":\"https://firebasestorage.googleapis.com/v0/b/book-e0228.appspot.com/o/icon_main%2Ficons8-search-48.png?alt=media&token=3e1c8522-5eb4-4432-9b7f-529ad2733553\",\"description\":\"Tìm kiếm\",\"layout_form\":\"../configuration/system_setting/layout/android/form/search\"},{\"fragment\":\"com.resshare.core.screen.DynamicFragmentRecyclerViewV2\",\"flow_chart\":\"\",\"icon\":\"https://firebasestorage.googleapis.com/v0/b/book-e0228.appspot.com/o/icon_main%2Ficons8-add-book-48.png?alt=media&token=95e59da8-6ed8-4b89-9340-8ddbb99a45c0\",\"description\":\"Thêm sách\",\"layout_form\":\"../configuration/system_setting/layout/android/form/add_book\"},{\"fragment\":\"com.resshare.core.screen.LocationDynamicActivity\",\"flow_chart\":\"\",\"icon\":\"https://firebasestorage.googleapis.com/v0/b/book-e0228.appspot.com/o/icon_main%2Ficons8-contact-50.png?alt=media&token=1080ac13-4f91-4d79-8562-cfe1e9e1f872\",\"description\":\"Thông tin liên hệ\",\"layout_form\":\"../configuration/system_setting/layout/android/form/my_location\"},{\"fragment\":\"com.resshare.core.screen.DynamicFragmentRecyclerView\",\"flow_chart\":\"\",\"icon\":\"https://firebasestorage.googleapis.com/v0/b/book-e0228.appspot.com/o/icon_main%2Ficons8-bookcase-64.png?alt=media&token=b52cc930-9f43-4a9c-9c55-b66292a182b2\",\"description\":\"Tủ sách của bạn\",\"layout_form\":\"../configuration/system_setting/layout/android/form/my_bookcase\"}]},\"application_script\":\"resshare_core\",\"script\":\"com.deflh.GetMainDashboardUI\"},\"view_type\":1,\"user_id_destination\":\"-NIVTrQEQRtKteCwnEjZ\",\"script_type\":\"script_type_grid_dashboard\",\"event\":\"event_dashboard_sesion_ios_2023_09_01_22_39_33_878\",\"main_dashboard_app\":true}";
	//	String s = JSON.stringify(request_data);
		Input requestJson = new Input();


		requestJson.setApplication("book");

		requestJson.setJson(request_data);


		Class<?> TypeClass = String.class;
		Object result = null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
		headers.setContentType(mediaType);


		//headers.setContentType(MediaType.APPLICATION_JSON);

		//headers.set("x-api-key", StartServiceListenerCore.x_api_key);

		HttpEntity entity = new HttpEntity(requestJson, headers);

	String 	REST_SERVICE_URI = "https://e3bcna3gk7.execute-api.ap-southeast-1.amazonaws.com/prod/request/input"  ;
		System.out.print("REST_SERVICE_URI: " + REST_SERVICE_URI);
		result = restTemplate.postForObject(REST_SERVICE_URI, entity, TypeClass);
		System.out.print("REST_SERVICE_URI: " + result);


	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) {

		try {
			String methodName = null;
			AnoMethod annoMethod = method.getAnnotation(AnoMethod.class);
			if (annoMethod == null)
				methodName = method.getName();
			else
				methodName = annoMethod.value();

			if ((StartServiceListenerCore.http == null) || "".equals(StartServiceListenerCore.http))
				StartServiceListenerCore.http = "http";


			String REST_SERVICE_URI = StartServiceListenerCore.GATEWAY_URI.trim();
			if(!REST_SERVICE_URI.startsWith("http"))
				REST_SERVICE_URI = StartServiceListenerCore.http + "://" + StartServiceListenerCore.GATEWAY_URI;// +"/";
			RestTemplate restTemplate = new RestTemplate();
			Object requestJson = args[0];
			Class<?> TypeClass = method.getReturnType();
			Object result = null;
			if ("amazonaws".equals(StartServiceListenerCore.gateway_type)) {
				HttpHeaders headers = new HttpHeaders();
				MediaType mediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
				headers.setContentType(mediaType);

//				if ((StartServiceListenerCore.x_api_key != null) &&  !"".equals(StartServiceListenerCore.x_api_key)
//						&&  !"none".equals(StartServiceListenerCore.x_api_key))
//					headers.set("x-api-








				HttpEntity entity = new HttpEntity(requestJson, headers);
				AnoModule annoModulde = method.getDeclaringClass().getAnnotation(AnoModule.class);
				System.out.println("annoModulde: " + annoModulde.value());
				System.out.println("methodName: " + methodName);

				REST_SERVICE_URI = REST_SERVICE_URI + "/" + annoModulde.value() + "/" + methodName ;
				System.out.println("REST_SERVICE_URI: " + REST_SERVICE_URI);
				try {
				result = restTemplate.postForObject(REST_SERVICE_URI, entity, TypeClass);
				} catch (Exception e1) {

					e1.printStackTrace();

				}
				System.out.println("entity: " + entity);
				System.out.println("requestJson: " + requestJson);
			} else {

				AnoModule annoModulde = method.getDeclaringClass().getAnnotation(AnoModule.class);
				System.out.println("annoModulde: " + annoModulde.value());
				System.out.println("methodName: " + methodName);

				REST_SERVICE_URI = REST_SERVICE_URI + "/" + annoModulde.value() + "/" + methodName ;
				System.out.println("REST_SERVICE_URI: " + REST_SERVICE_URI);
				result = restTemplate.postForObject(REST_SERVICE_URI, requestJson, TypeClass);
			}
			return result;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}
	public Object invokeold(Object proxy, Method method, Object[] args) {

		try {
			String methodName = null;
			AnoMethod annoMethod = method.getAnnotation(AnoMethod.class);
			if (annoMethod == null)
				methodName = method.getName();
			else
				methodName = annoMethod.value();

			if ((StartServiceListenerCore.http == null) || "".equals(StartServiceListenerCore.http))
				StartServiceListenerCore.http = "http";

			String REST_SERVICE_URI = StartServiceListenerCore.http + "://" + StartServiceListenerCore.GATEWAY_URI;// +"/";
			RestTemplate restTemplate = new RestTemplate();
			Object requestJson = args[0];
			Class<?> TypeClass = method.getReturnType();
			Object result = null;
			if ("amazonaws".equals(StartServiceListenerCore.gateway_type)) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				headers.set("x-api-key", StartServiceListenerCore.x_api_key);

				HttpEntity entity = new HttpEntity(requestJson, headers);
				System.out.print("REST_SERVICE_URI: " + REST_SERVICE_URI);
				result = restTemplate.postForObject(REST_SERVICE_URI, entity, TypeClass);
			} else {

				AnoModule annoModulde = method.getDeclaringClass().getAnnotation(AnoModule.class);
				System.out.println("annoModulde: " + annoModulde.value());
				System.out.println("methodName: " + methodName);

				REST_SERVICE_URI = REST_SERVICE_URI + "/" + annoModulde.value() + "/" + methodName ;
				result = restTemplate.postForObject(REST_SERVICE_URI, requestJson, TypeClass);
			}
			return result;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}
}