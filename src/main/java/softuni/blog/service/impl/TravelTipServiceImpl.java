package softuni.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import softuni.blog.data.entity.TravelTip;
import softuni.blog.data.serviceModel.TravelTipAddServiceModel;
import softuni.blog.repository.TravelTipRepository;
import softuni.blog.service.TravelTipService;
import softuni.blog.web.apiModel.TravelTipCreateResponseModel;
import softuni.blog.web.apiModel.TravelTipResponseModel;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelTipServiceImpl implements TravelTipService {

    private final TravelTipRepository travelTipRepository;
    private final ModelMapper modelMapper;

    public TravelTipServiceImpl(TravelTipRepository travelTipRepository, ModelMapper modelMapper) {
        this.travelTipRepository = travelTipRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<List<TravelTipResponseModel>> findAllTips() {
        if(this.travelTipRepository.count() == 0){
           return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<TravelTipResponseModel> allTips = this.travelTipRepository.findAll().stream().map(tip ->
             this.modelMapper.map(tip,TravelTipResponseModel.class)).collect(Collectors.toList());

        return ResponseEntity.ok().body(allTips);
    }

    @Override
    public ResponseEntity<TravelTipCreateResponseModel> createTip(TravelTipAddServiceModel travelTipAddServiceModel) {
        if(this.travelTipRepository.existsByTitle(travelTipAddServiceModel.getTitle())){
            return ResponseEntity.status(HttpStatus.IM_USED).build();
        }

        TravelTip travelTip = this.modelMapper.map(travelTipAddServiceModel,TravelTip.class);

        this.travelTipRepository.saveAndFlush(travelTip);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
