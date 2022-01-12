package com.uam.pracowniaprogramowaniaprojekt.service.impl;

import com.uam.pracowniaprogramowaniaprojekt.dao.ManufacturerRepository;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.ManufacturerDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.NewManufacturerDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.embedded.Address;
import com.uam.pracowniaprogramowaniaprojekt.domain.embedded.ContactDetails;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.Manufacturer;
import com.uam.pracowniaprogramowaniaprojekt.mapper.ManufacturerMapper;
import com.uam.pracowniaprogramowaniaprojekt.mapper.NewManufacturerMapper;
import com.uam.pracowniaprogramowaniaprojekt.service.ManufacturerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ManufacturerServiceImplTest {

    @Mock
    ManufacturerRepository manufacturerRepository;

    @Mock
    ManufacturerMapper manufacturerMapper;

    @Mock
    NewManufacturerMapper newManufacturerMapper;

    @InjectMocks
    ManufacturerServiceImpl manufacturerService;

    ManufacturerDTO manufacturerDTO;
    Manufacturer manufacturerEntity;
    NewManufacturerDTO newManufacturerDTO;

    @BeforeEach
    void setUp() {
        Address address = new Address();
        address.setCity("Zakopane");
        address.setCountry("Polska");
        address.setHomeNumber("15");
        address.setStreet("ul. Grzybowa");
        address.setPostalCode("77-899");
        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setEmail("newmanufacturer@com.pl");
        contactDetails.setPhoneNumber("000 000 000");
        contactDetails.setWebsite("mmm.com.pl");

        manufacturerDTO = new ManufacturerDTO();
        manufacturerDTO.setName("Sample Manufacturer");
        manufacturerDTO.setAddress(address);
        manufacturerDTO.setContactDetails(contactDetails);
        manufacturerDTO.setId(1L);

        manufacturerEntity = new Manufacturer();
        manufacturerEntity.setName("Sample Manufacturer Entity");
        manufacturerEntity.setAddress(address);
        manufacturerEntity.setContactDetails(contactDetails);
        manufacturerEntity.setId(1L);

        newManufacturerDTO = new NewManufacturerDTO();
        newManufacturerDTO.setName("New Sample Manufacturer");
        newManufacturerDTO.setAddress(address);
        newManufacturerDTO.setContactDetails(contactDetails);
    }

    @Test
    void shouldfindAllManufacturersReturnListOfManufacturerDTO() {
        //given
        List<ManufacturerDTO> DTOList = new ArrayList<>();
        DTOList.add(manufacturerDTO);
        DTOList.add(manufacturerDTO);
        List<Manufacturer> entityList = new ArrayList<>();
        entityList.add(manufacturerEntity);
        entityList.add(manufacturerEntity);
        Mockito.when(manufacturerRepository.findAll()).thenReturn(entityList);
        Mockito.when(manufacturerMapper.mapToDtos(entityList)).thenReturn(DTOList);
        //when
        manufacturerService.findAllManufacturers();
        //then
        Mockito.verify(manufacturerRepository).findAll();
        Mockito.verify(manufacturerMapper).mapToDtos(any(ArrayList.class));
    }

    @Test
    void shouldFindByIdReturnManufacturerWithSearchedId() {
        //given
        Long searchedId = 1L;
        Mockito.when(manufacturerRepository.findById(searchedId)).thenReturn(java.util.Optional.ofNullable(manufacturerEntity));
        Mockito.when(manufacturerMapper.mapToDto(manufacturerEntity)).thenReturn(manufacturerDTO);
        //when
        manufacturerService.findById(searchedId);
        //then
        Mockito.verify(manufacturerRepository).findById(searchedId);
        Mockito.verify(manufacturerMapper).mapToDto(any(Manufacturer.class));
    }

    @Test
    void shouldFindByIdThrowExceptionWhenIfDoesNotExist() {
        //given
        Long nonExistedId = 10000L;
        Mockito.when(manufacturerRepository.findById(nonExistedId)).thenThrow(EntityNotFoundException.class);
        //when then
        assertThrows(EntityNotFoundException.class, () -> manufacturerService.findById(nonExistedId));
    }

    @Test
    void saveManufacturer() {
        //given
        Mockito.when(newManufacturerMapper.mapToEntity(any(NewManufacturerDTO.class))).thenReturn(manufacturerEntity);
        Mockito.when(manufacturerRepository.save(manufacturerEntity)).thenReturn(manufacturerEntity);
        Mockito.when(manufacturerMapper.mapToDto(manufacturerEntity)).thenReturn(manufacturerDTO);
        //when
        manufacturerService.saveManufacturer(newManufacturerDTO);
        //then
        Mockito.verify(newManufacturerMapper).mapToEntity(any(NewManufacturerDTO.class));
        Mockito.verify(manufacturerRepository).save(any(Manufacturer.class));
        Mockito.verify(manufacturerMapper).mapToDto(any(Manufacturer.class));
    }

    @Test
    void updateManufacturer() {
        //given
        Long idOfUpdatedManufacturer = 1L;
        Mockito.when(manufacturerRepository.findById(idOfUpdatedManufacturer)).thenReturn(java.util.Optional.ofNullable(manufacturerEntity));
        Mockito.when(manufacturerRepository.save(manufacturerEntity)).thenReturn(manufacturerEntity);
        Mockito.when(manufacturerMapper.mapToDto(manufacturerEntity)).thenReturn(manufacturerDTO);
        //when
        manufacturerService.updateManufacturer(idOfUpdatedManufacturer, manufacturerDTO);
        //then
        Mockito.verify(manufacturerRepository).findById(any(Long.class));
        Mockito.verify(manufacturerRepository).save(any(Manufacturer.class));
        Mockito.verify(manufacturerMapper).mapToDto(any(Manufacturer.class));
    }

    @Test
    void deleteManufacturer() {
        //given
        Long idOfManufacturerToBeRemoved = 1L;
        Mockito.when(manufacturerRepository.findById(idOfManufacturerToBeRemoved)).thenReturn(java.util.Optional.ofNullable(manufacturerEntity));
        Mockito.doNothing().when(manufacturerRepository).deleteById(idOfManufacturerToBeRemoved);
        Mockito.when(manufacturerMapper.mapToDto(manufacturerEntity)).thenReturn(manufacturerDTO);
        //when
        manufacturerService.deleteManufacturer(idOfManufacturerToBeRemoved);
        //then
        Mockito.verify(manufacturerRepository).findById(any(Long.class));
        Mockito.verify(manufacturerRepository).deleteById(any(Long.class));
        Mockito.verify(manufacturerMapper).mapToDto(any(Manufacturer.class));
    }
}