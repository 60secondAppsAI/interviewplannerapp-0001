package com.interviewplannerapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.interviewplannerapp.domain.Account;
import com.interviewplannerapp.dto.AccountDTO;
import com.interviewplannerapp.dto.AccountSearchDTO;
import com.interviewplannerapp.dto.AccountPageDTO;
import com.interviewplannerapp.dto.AccountConvertCriteriaDTO;
import com.interviewplannerapp.service.GenericService;
import com.interviewplannerapp.dto.common.RequestDTO;
import com.interviewplannerapp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface AccountService extends GenericService<Account, Integer> {

	List<Account> findAll();

	ResultDTO addAccount(AccountDTO accountDTO, RequestDTO requestDTO);

	ResultDTO updateAccount(AccountDTO accountDTO, RequestDTO requestDTO);

    Page<Account> getAllAccounts(Pageable pageable);

    Page<Account> getAllAccounts(Specification<Account> spec, Pageable pageable);

	ResponseEntity<AccountPageDTO> getAccounts(AccountSearchDTO accountSearchDTO);
	
	List<AccountDTO> convertAccountsToAccountDTOs(List<Account> accounts, AccountConvertCriteriaDTO convertCriteria);

	AccountDTO getAccountDTOById(Integer accountId);







}





