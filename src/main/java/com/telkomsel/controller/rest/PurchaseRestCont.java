package com.telkomsel.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telkomsel.entity.*;
import com.telkomsel.entity.model.RestResponse;
import com.telkomsel.logger.Debug;
import com.telkomsel.logger.EDR;
import com.telkomsel.services.*;
import com.telkomsel.utils.ConstantVariable;
import com.telkomsel.utils.ExceptionUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping(value = "/r/purchase")
public class PurchaseRestCont {
    @Autowired
    private BankService bankService;
    @Autowired
    private ProductService productService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private UserService userService;

//    checkout

    @RequestMapping(method = RequestMethod.POST, value = "/checkout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> checkout(@RequestBody String o, Principal principal) throws JsonProcessingException {
        User auth = (User) ((Authentication) principal).getPrincipal();
        RestResponse response = new RestResponse();
        ResponseEntity<Object> restResponse = null;
        ObjectMapper mapper = new ObjectMapper();
        JSONObject obj = new JSONObject(o);
        ActPurchase purchase = new ActPurchase();
        CnfProduct product = new CnfProduct();
        Date now = new Date();
        Integer price, code;
        Calendar expire_date = Calendar.getInstance();
        expire_date.setTime(now);
        expire_date.add(Calendar.HOUR_OF_DAY, 24);
        Debug.info("== start to checkout ==");
        try{
            Debug.info("== check stock before checkout ==");
            Optional<CnfProduct> optProd = productService.findById(obj.getInt("product_id"));
            Optional<CnfBank> optBank = bankService.findById(obj.getInt("bank_id"));
            Optional<AppUser> optUser = userService.findByEmailOptional(auth.getUsername());
            if(optProd.isPresent() && optBank.isPresent() && optUser.isPresent()){
                if(optProd.get().getQty() >= Integer.parseInt(obj.get("qty").toString())){
                    product = optProd.get();
                    price = Integer.parseInt(obj.get("price").toString()) +Integer.parseInt(String.format("%03d", new Random().nextInt(999)));
                    product.setQty(optProd.get().getQty() - Integer.parseInt(obj.get("qty").toString()));
                    code = Integer.valueOf(String.valueOf(price).substring(String.valueOf(price).length() - 3));
                    purchase.setBank(optBank.get());
                    purchase.setProduct(optProd.get());
                    purchase.setUser(optUser.get());
                    purchase.setQty(Integer.parseInt(obj.get("qty").toString()));
                    purchase.setPrice(price);
                    purchase.setPayment_code(code);
                    purchase.setStatus("U");
                    purchase.setPurchase_date(now);
                    purchase.setExpired_date(expire_date.getTime());

                    purchaseService.saveWithProduct(purchase, product);
                }else{
                    response.setContent(ConstantVariable.OOS_DESC);
                    response.setStatus_code(HttpStatus.OK);
                    response.setStatus_desc(ConstantVariable.FAILED);
                    Debug.info("== product out of stock ==");
                }
            }else{
                response.setContent(ConstantVariable.PARAM_INVALID_DESC);
                response.setStatus_code(HttpStatus.BAD_REQUEST);
                response.setStatus_desc(ConstantVariable.FAILED);
                Debug.info("== stock failed to check ==");
            }

            response.setStatus_code(HttpStatus.OK);
            response.setStatus_desc(ConstantVariable.SUCCESS_DESC);
            response.setContent(mapper.writeValueAsString(purchase));
            Debug.info("== checkout success ==");

            restResponse = new ResponseEntity<Object>(response,response.getStatus_code());
            Debug.info("== buyer successfully to checkout ==");
            EDR.record("/r/purchase/checkout == Data : "+purchase.toString());
        }catch (Exception ex){
            Debug.error("== checkout was failed ==", new Exception(ex.getMessage()));
            restResponse = ExceptionUtils.translate(ex);
        }
        return restResponse;
    }

//    payment


    @RequestMapping(method = RequestMethod.POST, value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> payment(@RequestBody String o, Principal principal) throws JsonProcessingException {
        User auth = (User) ((Authentication) principal).getPrincipal();
        RestResponse response = new RestResponse();
        ResponseEntity<Object> restResponse = null;
        ObjectMapper mapper = new ObjectMapper();
        JSONObject obj = new JSONObject(o);
        ActPurchase purchase = new ActPurchase();
        CnfProduct product = new CnfProduct();
        Debug.info("== start to payment ==");
        try{
            Debug.info("== check stock before payment ==");
            Optional<ActPurchase> optPurchase = purchaseService.findById(obj.getInt("purhcase_id"));
            if(optPurchase.isPresent()){
                if(new Date().before(optPurchase.get().getExpired_date())){
                    purchase.setTransfer_date(new Date());
                    purchase.setStatus("P");
                }else{
                    Optional<CnfProduct> optProd = productService.findById(optPurchase.get().getProduct().getProductId());
                    if(optProd.isPresent()){
                        product = optProd.get();
                        product.setQty(product.getQty() + optPurchase.get().getQty());

                        purchase.setStatus("E");
                    }

                    response.setContent(ConstantVariable.PAYMENT_EXPIRED_DESC);
                    response.setStatus_code(HttpStatus.OK);
                    response.setStatus_desc(ConstantVariable.FAILED);
                    Debug.info("== transaction has expired ==");
                }

                purchaseService.saveWithProduct(purchase, product);
            }else{
                response.setContent(ConstantVariable.PARAM_INVALID_DESC);
                response.setStatus_code(HttpStatus.BAD_REQUEST);
                response.setStatus_desc(ConstantVariable.FAILED);
                Debug.info("== stock failed to check ==");
            }

            response.setStatus_code(HttpStatus.OK);
            response.setStatus_desc(ConstantVariable.SUCCESS_DESC);
            response.setContent(mapper.writeValueAsString(purchase));
            Debug.info("== payment success ==");

            restResponse = new ResponseEntity<Object>(response,response.getStatus_code());
            Debug.info("== buyer successfully to pay ==");
            EDR.record("/r/purchase/payment == Data : "+purchase.toString());
        }catch (Exception ex){
            Debug.error("== payment was failed ==", new Exception(ex.getMessage()));
            restResponse = ExceptionUtils.translate(ex);
        }
        return restResponse;
    }

}
