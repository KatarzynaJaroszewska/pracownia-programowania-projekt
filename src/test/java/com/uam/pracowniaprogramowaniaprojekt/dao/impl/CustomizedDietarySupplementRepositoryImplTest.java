package com.uam.pracowniaprogramowaniaprojekt.dao.impl;

import com.uam.pracowniaprogramowaniaprojekt.dao.CustomizedDietarySupplementRepository;
import com.uam.pracowniaprogramowaniaprojekt.domain.dto.DietarySupplementSearchCriteriaDTO;
import com.uam.pracowniaprogramowaniaprojekt.domain.entity.DietarySupplement;
import com.uam.pracowniaprogramowaniaprojekt.domain.enumeration.ProductCategory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@ActiveProfiles("hsql")
@Transactional
class CustomizedDietarySupplementRepositoryImplTest {

    @Autowired
    private CustomizedDietarySupplementRepository repository;

    private SoftAssertions softAssertions = new SoftAssertions();

    @Test
    @Transactional
    public void shouldReturnAllRecordsWhenAllPredicatesAreNull() {
        //given
        DietarySupplementSearchCriteriaDTO criteria = new DietarySupplementSearchCriteriaDTO(null, null, null, null);
        int numberOfAllRecords = 5;
        //when
        List<DietarySupplement> resultList = repository.findDietarySupplementsBySearchCriteria(criteria);
        //then
        softAssertions.assertThat(resultList).isNotNull();
        softAssertions.assertThat(resultList).isNotEmpty();
        softAssertions.assertThat(resultList).hasSize(numberOfAllRecords);
        softAssertions.assertAll();
    }

    @Test
    @Transactional
    public void shouldReturnOneRecordWhenISearchByProductCategoryForKids() {
        //given
        String searchedProductCategory = ProductCategory.FOR_KIDS.toString();
        DietarySupplementSearchCriteriaDTO criteria = new DietarySupplementSearchCriteriaDTO(null, null, searchedProductCategory, null);
        int numberOfProductsForKids = 1;
        //when
        List<DietarySupplement> resultList = repository.findDietarySupplementsBySearchCriteria(criteria);
        //then
        softAssertions.assertThat(resultList).isNotNull();
        softAssertions.assertThat(resultList).isNotEmpty();
        softAssertions.assertThat(resultList).hasSize(numberOfProductsForKids);
        softAssertions.assertThat(resultList.get(0).getProductCategory().toString()).isEqualTo(searchedProductCategory);
        softAssertions.assertAll();
    }

}