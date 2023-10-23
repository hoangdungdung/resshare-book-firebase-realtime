package com.resshare.springboot;

import java.net.URI;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.resshare.framework.core.controller.RestInputApiController;
import com.resshare.framework.core.service.ResFirebaseReference;
import com.resshare.framework.model.Input;
import com.resshare.framework.model.Output;



        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.gson.Gson;
        import com.resshare.framework.core.service.ResFirebaseReference;
        import com.resshare.framework.model.Input;
        import com.resshare.framework.model.Output;
        import java.net.URI;
        import java.util.Map;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.http.HttpHeaders;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.RequestBody;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.RestController;
        import org.springframework.web.client.RestTemplate;
        import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping({"/api"})
public class BookRestInputApiController {
    public static final Logger logger = LoggerFactory.getLogger(com.resshare.framework.core.controller.RestInputApiController.class);
    public static String REST_SERVICE_URI = "http://localhost:8088/api";

    public BookRestInputApiController() {
    }


    @PostMapping(value = "/input")
    public ResponseEntity<?> request(@RequestBody Input input, UriComponentsBuilder ucBuilder) {
        logger.info("POST input : {}", input);
        if (this.validate(input)) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            try {
                Gson gson = new Gson();
                Map map = input.getJsonmap();
                if (input.getJson() != null) {
                    map = (Map)gson.fromJson(input.getJson(), Map.class);
                }

                String sKey = null;
                String dataCollection = input.getDataCollection();
                if ("draft/core/flowchart_execute".equals(dataCollection)) {
                    dataCollection = "../" + dataCollection;
                }

                if ("draft/core/seach_app".equals(dataCollection)) {
                    dataCollection = "../" + dataCollection;
                }

                if ("draft/core/get_home_layout".equals(dataCollection)) {
                    dataCollection = "../" + dataCollection;
                }

                if ("draft/core/get_home_default_layout".equals(dataCollection)) {
                    dataCollection = "../" + dataCollection;
                }

                if ("draft/core/get_layout_data".equals(dataCollection)) {
                    dataCollection = "../" + dataCollection;
                }

                if ("draft/core/get_data".equals(dataCollection)) {
                    dataCollection = "../" + dataCollection;
                }

                if ("draft/core/get_main_dashboard_app".equals(dataCollection)) {
                    dataCollection = "../" + dataCollection;
                }

                String path_load_form_script = ResFirebaseReference.getInputPathReference(dataCollection);
                DatabaseReference reference = database.getReference(path_load_form_script);
                if (map != null) {
                    if (map.containsKey("key_use_user_id") && map.containsKey("user_id") && map.get("key_use_user_id").equals(true)) {
                        sKey = (String)map.get("user_id");
                    }

                    if (sKey != null) {
                        reference.child(sKey).setValue(map);
                    } else if (input.getId() != null) {
                        reference.child(input.getId()).setValue(map);
                    } else {
                        reference.push().setValue(map);
                    }
                }
            } catch (Exception var10) {
                var10.printStackTrace();
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/input/{aplication}").buildAndExpand(new Object[]{input.getApplication()}).toUri());
        return new ResponseEntity(headers, HttpStatus.OK);
    }

    private boolean validate(Input input) {
        return true;
    }

    private static void postOutput(Output output) {
        System.out.println("Testing postOutput Output API----------");
        RestTemplate restTemplate = new RestTemplate();
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/output/", output, new Object[]{Output.class});
        System.out.println("Location : " + uri.toASCIIString());
    }

    @RequestMapping(
            value = {"/output/"},
            method = {RequestMethod.POST}
    )
    public ResponseEntity<?> response(@RequestBody Output output, UriComponentsBuilder ucBuilder) {
        logger.info("POST Output : {}", output);
        postOutput(output);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/output/{aplication}").buildAndExpand(new Object[]{output.getAplication()}).toUri());
        return new ResponseEntity(headers, HttpStatus.OK);
    }
}
