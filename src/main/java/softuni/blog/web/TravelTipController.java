package softuni.blog.web;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import softuni.blog.data.serviceModel.TravelTipAddServiceModel;
import softuni.blog.service.TravelTipService;
import softuni.blog.service.UserService;
import softuni.blog.web.apiModel.TravelTipCreateResponseModel;
import softuni.blog.web.apiModel.TravelTipResponseModel;

import java.util.List;

@RestController

public class TravelTipController {

    private final TravelTipService travelTipService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public TravelTipController(TravelTipService travelTipService, UserService userService, ModelMapper modelMapper) {
        this.travelTipService = travelTipService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/travel-tips")
    public ResponseEntity<List<TravelTipResponseModel>> showTips(){
        return this.travelTipService.findAllTips();
    }

    @GetMapping("/tips/add")
    public ResponseEntity<String> addTip(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tips/add")
    public ResponseEntity<TravelTipCreateResponseModel> addTipConfirm(@RequestBody TravelTipCreateResponseModel travelTipCreateResponseModel){
        TravelTipAddServiceModel travelTipAddServiceModel = this.modelMapper.map(travelTipCreateResponseModel,TravelTipAddServiceModel.class);
        travelTipAddServiceModel.setAuthor(this.userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return this.travelTipService.createTip(travelTipAddServiceModel);
    }
}
