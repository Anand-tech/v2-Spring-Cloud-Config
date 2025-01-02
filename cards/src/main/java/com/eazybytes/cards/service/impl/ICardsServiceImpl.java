package com.eazybytes.cards.service.impl;

import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.exception.CardsAlreadyExistsException;
import com.eazybytes.cards.exception.ResourceNotFoundException;
import com.eazybytes.cards.mapper.CardsMapper;
import com.eazybytes.cards.repository.CardsRepository;
import com.eazybytes.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.smartcardio.Card;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class ICardsServiceImpl implements ICardsService {

   private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {

       Optional<Cards> cards = cardsRepository.findByMobileNumber(mobileNumber);

        if(cards.isPresent())
        {
            throw new CardsAlreadyExistsException("The Cards already present in the database with the given mobile Number"+mobileNumber);
        }
        else
        {
            cardsRepository.save(createNewCard(mobileNumber));
        }
    }

    private Cards createNewCard(String mobileNumber)
    {
        Cards cards = new Cards();


        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        cards.setCardNumber(Long.toString(randomCardNumber));
        cards.setMobileNumber(mobileNumber);
        cards.setCardType(CardsConstants.CREDIT_CARD);
        cards.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        cards.setAmountUsed(0);
        cards.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);

        return cards;

    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {

        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->   new ResourceNotFoundException("Cards","mobileNumber",mobileNumber)
        );
        return CardsMapper.maptoCardsDto(cards,new CardsDto());
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                ()->new ResourceNotFoundException("Card","CardNumber",cardsDto.getCardNumber())
        );

        cardsRepository.save(CardsMapper.maptoCards(cardsDto,cards));

        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {

        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Card","mobileNumber",mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());

        return true;
    }
}
