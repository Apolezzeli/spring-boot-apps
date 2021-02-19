package ar.com.ada.second.online.endpointsdefinition.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/main")

public class MainController {
    //DELETE, POST, GET, PUT/PATCH => path (segmento de la URL: localhost:8080/{segment})

    @GetMapping({"/", "/sayHi", ""})
    public ResponseEntity sayHelloWorld(){
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("hello", "world");

        return ResponseEntity.ok().body(responseBody);
        //return ResponseEntity.ok(responseBody);
    }

    @GetMapping({"/sayOther"})
    public ResponseEntity sayOther(){
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("hello", "other");

        return ResponseEntity.ok().body(responseBody);
        //return ResponseEntity.ok(responseBody);
    }

}
