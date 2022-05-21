package com.telkomsel.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telkomsel.entity.CnfBank;
import com.telkomsel.entity.CnfCategory;
import com.telkomsel.entity.CnfProduct;
import com.telkomsel.entity.model.RestResponse;
import com.telkomsel.logger.Debug;
import com.telkomsel.logger.EDR;
import com.telkomsel.services.BankService;
import com.telkomsel.services.CategoryService;
import com.telkomsel.services.ProductService;
import com.telkomsel.utils.ConstantVariable;
import com.telkomsel.utils.ExceptionUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/r/config")
public class ConfigRestCont {
    @Autowired
    private BankService bankService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

//    Bank

    @RequestMapping(method = RequestMethod.POST, value = "/list/bank", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listBank() throws JsonProcessingException {
        RestResponse response = new RestResponse();
        ResponseEntity<Object> restResponse = null;
        ObjectMapper mapper = new ObjectMapper();
        Debug.info("== start to listing bank ==");
        try{
            Iterable<CnfBank> bank = bankService.findAll();
            response.setStatus_code(HttpStatus.OK);
            response.setStatus_desc(ConstantVariable.SUCCESS_DESC);
            response.setContent(mapper.writeValueAsString(bank));
            Debug.info("== list bank success ==");

            restResponse = new ResponseEntity<Object>(response,response.getStatus_code());
            Debug.info("== bank successfully to listed ==");
            EDR.record("/r/config/list/bank == Data : "+bank);
        }catch (Exception ex){
            Debug.error("== delete bank it was failed ==", new Exception(ex.getMessage()));
            restResponse = ExceptionUtils.translate(ex);
        }
        return restResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/submit/bank", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> submitBank(@RequestBody String o) throws JsonProcessingException {
        RestResponse response = new RestResponse();
        ResponseEntity<Object> restResponse = null;
        Debug.info("== starting submit bank ==");
        JSONObject obj = new JSONObject(o);
        CnfBank bank = new CnfBank();
        String bank_name = obj.get("name").toString();
        String acc_name = obj.get("acc_name").toString();
        String acc_num = obj.get("acc_num").toString();
        String balance = obj.get("balance").toString();
        try{
            if(!obj.has("bank_id")){
                bank.setBank_name(bank_name);
                bank.setAccount_name(acc_name);
                bank.setAccount_number(acc_num);
                bank.setBalance(0);
            }else{
                Optional<CnfBank> optBank = bankService.findById(obj.getInt("bank_id"));
                if(optBank.isPresent()){
                    bank = optBank.get();
                    bank.setBankId(bank.getBankId());
                    bank.setBank_name(bank_name.equals("") ? bank.getBank_name() : bank_name);
                    bank.setAccount_name(acc_name.equals("") ? bank.getAccount_name() : acc_name);
                    bank.setAccount_number(acc_num.equals("") ? bank.getAccount_number() : acc_num);
                    bank.setBalance(balance.equals("") ? bank.getBalance() :  Integer.valueOf(balance));
                }else{
                    response.setContent(ConstantVariable.PARAM_INVALID_DESC);
                    response.setStatus_code(HttpStatus.BAD_REQUEST);
                    response.setStatus_desc(ConstantVariable.FAILED);
                    Debug.info("== bank failed to submit ==");
                }
            }

            bankService.save(bank);
            response.setStatus_code(HttpStatus.OK);
            response.setStatus_desc(ConstantVariable.SUCCESS_DESC);
            response.setContent(HttpStatus.OK.toString());
            restResponse = new ResponseEntity<Object>(response,response.getStatus_code());
            Debug.info("== bank successfully submited ==");
            EDR.record("/r/config/submit/bank == Data : "+bank.toString());
        }catch (Exception ex){
            Debug.error("== submit bank it was failed ==", new Exception(ex.getMessage()));
            restResponse = ExceptionUtils.translate(ex);
        }
        return restResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete/bank", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteBank(@RequestBody String o) throws JsonProcessingException {
        RestResponse response = new RestResponse();
        ResponseEntity<Object> restResponse = null;
        Debug.info("== starting delete bank ==");
        JSONObject obj = new JSONObject(o);
        try{
            if(!obj.has("bank_id")){
                response.setContent(ConstantVariable.PARAM_INVALID_DESC);
                response.setStatus_code(HttpStatus.BAD_REQUEST);
                response.setStatus_desc(ConstantVariable.FAILED);
                Debug.info("== bank failed to delete ==");
            }else{
                Optional<CnfBank> optBank = bankService.findById(obj.getInt("bank_id"));
                if(optBank.isPresent()){
                    bankService.delete(optBank.get());
                }else{
                    response.setContent(ConstantVariable.PARAM_INVALID_DESC);
                    response.setStatus_code(HttpStatus.BAD_REQUEST);
                    response.setStatus_desc(ConstantVariable.FAILED);
                    Debug.info("== bank failed to delete ==");
                }
            }

            response.setStatus_code(HttpStatus.OK);
            response.setStatus_desc(ConstantVariable.SUCCESS_DESC);
            response.setContent(HttpStatus.OK.toString());
            restResponse = new ResponseEntity<Object>(response,response.getStatus_code());
            Debug.info("== bank successfully deleted ==");
            EDR.record("/r/config/delete/bank == Bank ID : "+obj.getInt("bank_id"));
        }catch (Exception ex){
            Debug.error("== delete bank it was failed ==", new Exception(ex.getMessage()));
            restResponse = ExceptionUtils.translate(ex);
        }
        return restResponse;
    }

//    Category

    @RequestMapping(method = RequestMethod.POST, value = "/list/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listCategory() throws JsonProcessingException {
        RestResponse response = new RestResponse();
        ResponseEntity<Object> restResponse = null;
        ObjectMapper mapper = new ObjectMapper();
        Debug.info("== start to listing category ==");
        try{
            Iterable<CnfCategory> category = categoryService.findAll();
            response.setStatus_code(HttpStatus.OK);
            response.setStatus_desc(ConstantVariable.SUCCESS_DESC);
            response.setContent(mapper.writeValueAsString(category));
            Debug.info("== list category success ==");

            restResponse = new ResponseEntity<Object>(response,response.getStatus_code());
            Debug.info("== category successfully to listed ==");
            EDR.record("/r/config/list/category == Data : "+category);
        }catch (Exception ex){
            Debug.error("== list category it was failed ==", new Exception(ex.getMessage()));
            restResponse = ExceptionUtils.translate(ex);
        }
        return restResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/submit/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> submitCategory(@RequestBody String o) throws JsonProcessingException {
        RestResponse response = new RestResponse();
        ResponseEntity<Object> restResponse = null;
        Debug.info("== starting submit category ==");
        JSONObject obj = new JSONObject(o);
        CnfCategory category = new CnfCategory();
        String cat_name = obj.get("cat_name").toString();
        String sub_name = obj.get("sub_name").toString();
        try{
            if(!obj.has("cat_id")){
                category.setCategory_name(cat_name);
                category.setSubcategory_name(sub_name);
            }else{
                Optional<CnfCategory> optCat = categoryService.findById(obj.getInt("cat_id"));
                if(optCat.isPresent()){
                    category = optCat.get();
                    category.setCategory_name(cat_name.equals("") ? category.getCategory_name() : cat_name);
                    category.setSubcategory_name(sub_name.equals("") ? category.getSubcategory_name() : sub_name);
                }else{
                    response.setContent(ConstantVariable.PARAM_INVALID_DESC);
                    response.setStatus_code(HttpStatus.BAD_REQUEST);
                    response.setStatus_desc(ConstantVariable.FAILED);
                    Debug.info("== category failed to submit ==");
                }
            }

            categoryService.save(category);
            response.setStatus_code(HttpStatus.OK);
            response.setStatus_desc(ConstantVariable.SUCCESS_DESC);
            response.setContent(HttpStatus.OK.toString());
            restResponse = new ResponseEntity<Object>(response,response.getStatus_code());
            Debug.info("== category successfully submited ==");
            EDR.record("/r/config/submit/category == Data : "+category.toString());
        }catch (Exception ex){
            Debug.error("== submit category it was failed ==", new Exception(ex.getMessage()));
            restResponse = ExceptionUtils.translate(ex);
        }
        return restResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteCategory(@RequestBody String o) throws JsonProcessingException {
        RestResponse response = new RestResponse();
        ResponseEntity<Object> restResponse = null;
        Debug.info("== starting delete category ==");
        JSONObject obj = new JSONObject(o);
        try{
            if(!obj.has("cat_id")){
                response.setContent(ConstantVariable.PARAM_INVALID_DESC);
                response.setStatus_code(HttpStatus.BAD_REQUEST);
                response.setStatus_desc(ConstantVariable.FAILED);
                Debug.info("== category failed to delete ==");
            }else{
                Optional<CnfCategory> optCat = categoryService.findById(obj.getInt("cat_id"));
                if(optCat.isPresent()){
                    categoryService.delete(optCat.get());

                    response.setStatus_code(HttpStatus.OK);
                    response.setStatus_desc(ConstantVariable.SUCCESS_DESC);
                    response.setContent(HttpStatus.OK.toString());
                }else{
                    response.setContent(ConstantVariable.PARAM_INVALID_DESC);
                    response.setStatus_code(HttpStatus.BAD_REQUEST);
                    response.setStatus_desc(ConstantVariable.FAILED);
                    Debug.info("== category failed to delete ==");
                }
            }
            restResponse = new ResponseEntity<Object>(response,response.getStatus_code());
            Debug.info("== category successfully deleted ==");
            EDR.record("/r/config/delete/category == Category ID : "+obj.getInt("cat_id"));
        }catch (Exception ex){
            Debug.error("== delete category it was failed ==", new Exception(ex.getMessage()));
            restResponse = ExceptionUtils.translate(ex);
        }
        return restResponse;
    }

//    Product

    @RequestMapping(method = RequestMethod.POST, value = "/list/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listProduct() throws JsonProcessingException {
        RestResponse response = new RestResponse();
        ResponseEntity<Object> restResponse = null;
        ObjectMapper mapper = new ObjectMapper();
        Debug.info("== start to listing product ==");
        try{
            Iterable<CnfProduct> products = productService.findAll();
            response.setStatus_code(HttpStatus.OK);
            response.setStatus_desc(ConstantVariable.SUCCESS_DESC);
            response.setContent(mapper.writeValueAsString(products));
            Debug.info("== list product success ==");

            restResponse = new ResponseEntity<Object>(response,response.getStatus_code());
            Debug.info("== products successfully to listed ==");
            EDR.record("/r/config/list/product == Data : "+products);
        }catch (Exception ex){
            Debug.error("== list product it was failed ==", new Exception(ex.getMessage()));
            restResponse = ExceptionUtils.translate(ex);
        }
        return restResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/submit/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> submitProduct(@RequestBody String o) throws JsonProcessingException {
        RestResponse response = new RestResponse();
        ResponseEntity<Object> restResponse = null;
        Debug.info("== starting submit product ==");
        JSONObject obj = new JSONObject(o);
        CnfProduct product = new CnfProduct();
        Integer cat_id = obj.getInt("cat_id");
        String pro_name = obj.get("pro_name").toString();
        String qty = obj.get("qty").toString();
        String price = obj.get("price").toString();
        try{
            Optional<CnfCategory> optCat = categoryService.findById(cat_id);
            if(!obj.has("product_id")){
                product.setCategory(optCat.get());
                product.setProduct_name(pro_name);
                product.setPrice(Integer.valueOf(price));
                product.setQty(Integer.valueOf(qty));
            }else{
                Optional<CnfProduct> optProd = productService.findById(obj.getInt("product_id"));
                if(optProd.isPresent()){
                    product = optProd.get();
                    product.setCategory(optCat.get());
                    product.setProduct_name(pro_name.equals("") ? product.getProduct_name() : pro_name);
                    product.setPrice(price.equals("") ? product.getPrice() : Integer.valueOf(price));
                    product.setQty(qty.equals("") ? product.getQty() : Integer.valueOf(qty));
                }else{
                    response.setContent(ConstantVariable.PARAM_INVALID_DESC);
                    response.setStatus_code(HttpStatus.BAD_REQUEST);
                    response.setStatus_desc(ConstantVariable.FAILED);
                    Debug.info("== product failed to submit ==");
                }
            }

            productService.save(product);
            response.setStatus_code(HttpStatus.OK);
            response.setStatus_desc(ConstantVariable.SUCCESS_DESC);
            response.setContent(HttpStatus.OK.toString());
            restResponse = new ResponseEntity<Object>(response,response.getStatus_code());
            Debug.info("== product successfully submited ==");
            EDR.record("/r/config/submit/product == Data : "+product.toString());
        }catch (Exception ex){
            Debug.error("== submit product it was failed ==", new Exception(ex.getMessage()));
            restResponse = ExceptionUtils.translate(ex);
        }
        return restResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteProduct(@RequestBody String o) throws JsonProcessingException {
        RestResponse response = new RestResponse();
        ResponseEntity<Object> restResponse = null;
        Debug.info("== starting delete product ==");
        JSONObject obj = new JSONObject(o);
        try{
            if(!obj.has("product_id")){
                response.setContent(ConstantVariable.PARAM_INVALID_DESC);
                response.setStatus_code(HttpStatus.BAD_REQUEST);
                response.setStatus_desc(ConstantVariable.FAILED);
                Debug.info("== product failed to delete ==");
            }else{
                Optional<CnfProduct> optCat = productService.findById(obj.getInt("product_id"));
                if(optCat.isPresent()){
                    productService.delete(optCat.get());

                    response.setStatus_code(HttpStatus.OK);
                    response.setStatus_desc(ConstantVariable.SUCCESS_DESC);
                    response.setContent(HttpStatus.OK.toString());
                }else{
                    response.setContent(ConstantVariable.PARAM_INVALID_DESC);
                    response.setStatus_code(HttpStatus.BAD_REQUEST);
                    response.setStatus_desc(ConstantVariable.FAILED);
                    Debug.info("== product failed to delete ==");
                }
            }
            restResponse = new ResponseEntity<Object>(response,response.getStatus_code());
            Debug.info("== product successfully deleted ==");
            EDR.record("/r/config/delete/product == Product ID : "+obj.getInt("product_id"));
        }catch (Exception ex){
            Debug.error("== delete product it was failed ==", new Exception(ex.getMessage()));
            restResponse = ExceptionUtils.translate(ex);
        }
        return restResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/submit/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> submitUser(@RequestBody String o) throws JsonProcessingException {
        RestResponse response = new RestResponse();
        ResponseEntity<Object> restResponse = null;
        return restResponse;
    }
}
