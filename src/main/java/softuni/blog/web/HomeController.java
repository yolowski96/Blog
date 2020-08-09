package softuni.blog.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public ResponseEntity<String> home(){

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/")
    public ResponseEntity<String> index(){
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
