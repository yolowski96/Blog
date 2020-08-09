package softuni.blog.service;


import org.springframework.http.ResponseEntity;
import softuni.blog.data.serviceModel.TravelTipAddServiceModel;
import softuni.blog.web.apiModel.TravelTipCreateResponseModel;
import softuni.blog.web.apiModel.TravelTipResponseModel;

import java.util.List;

public interface TravelTipService {

    ResponseEntity<List<TravelTipResponseModel>> findAllTips();

    ResponseEntity<TravelTipCreateResponseModel> createTip(TravelTipAddServiceModel travelTipAddServiceModel);
}
