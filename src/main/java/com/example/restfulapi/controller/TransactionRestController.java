package com.example.restfulapi.controller;

import com.example.restfulapi.model.Transaction;
import com.example.restfulapi.service.TransactionService;
import com.example.restfulapi.utils.Response;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionRestController {
    private final TransactionService transactionService;
    TransactionRestController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping("/all-transactions")
    public Response<PageInfo<Transaction>> getAllTransactions(@RequestParam (defaultValue = "1") int page, @RequestParam (defaultValue = "5") int size,  @RequestParam(defaultValue = "0", required = false) int id){
        try {
            PageInfo<Transaction> pageInfo = transactionService.getAllTransaction(page, size, id);
            return Response.<PageInfo<Transaction>>ok().setMessage("Successfully retrieved all transactions!").setPayload(pageInfo);

        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return Response.<PageInfo<Transaction>>exception().setMessage("Fail to retrieve all transactions!").setSuccess(false);
        }
    }

    @PostMapping("/new-transaction")
    public Response<Transaction> createTransaction(@Valid @RequestBody Transaction transaction){
        try{
            int rowAffected = transactionService.createNewTransaction(transaction);
            if(rowAffected>0){
                return Response.<Transaction>createSuccess().setMessage("Successfully created a transaction").setPayload(transaction);
            } else {
                return Response.<Transaction>badRequest().setMessage("Fail to create new transaction!").setSuccess(false);
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return Response.<Transaction>exception().setSuccess(false).setMessage("Fail to create new transaction!");
        }
    }

    @PutMapping("/{id}")
    public Response<Transaction> updateTransaction(@RequestBody Transaction transaction, @PathVariable int id){
        try {
            transaction.setId(id);
            int affectedRow = transactionService.updateTransaction(transaction, id);
            if(affectedRow>0){
                return Response.<Transaction>updateSuccess().setPayload(transaction).setMessage("Successfully updated a transaction with id "+ id);
            } else {
                return Response.<Transaction>notFound().setMessage("Can't find a transaction with id "+id).setSuccess(false);
            }
        } catch (Exception exception){
            exception.printStackTrace();
            return Response.<Transaction>exception().setMessage("Fail to update a transaction with id "+id);
        }
    }

    @DeleteMapping("/{id}")
    public Response<Transaction> deleteTransaction(@PathVariable int id){
        int affectedRow = transactionService.deleteTransaction(id);
        try {
            if(affectedRow>0){
                return Response.<Transaction>deleteSuccess().setMessage("Successfully deleted a transaction with id "+ id);
            } else {
                return Response.<Transaction>notFound().setMessage("Can't find a transaction with id "+id).setSuccess(false);
            }
        } catch (Exception exception){
            return Response.<Transaction>exception().setMessage("Fail to delete a transaction with id "+id).setSuccess(false);
        }

    }
}
