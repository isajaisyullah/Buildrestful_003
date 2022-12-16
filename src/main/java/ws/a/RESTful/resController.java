/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ws.a.RESTful;

import ws.a.RESTful.model.Product;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Isa Jaisyullah
 */

@RestController
public class resController {
    private static Map<String, Product> productRepo = new HashMap<>(); //membuat hashmap untuk menyimpan data
    static {
        Product honey = new Product(); //memanggil class Product.java yang di deklarasikan menjadi variabel honey
        honey.setId("1");  //menambahkan data id
        honey.setName("Honey"); //menambahkan data nama
        honey.setPrice(10000);
        honey.setDiscount(5);
        
        int total = honey.getPrice() - (honey.getPrice()*honey.getDiscount()/100);
        
        honey.setTotal(total);
        productRepo.put(honey.getId(), honey); //menambahkan data pada hashmap
        
//        Product almond = new Product(); //memanggil class Product.java yang di deklarasikan menjadi variabel almond
//        almond.setId("2"); //menambahkan data id
//        almond.setName("Almond"); //menambahkan data nama
//        productRepo.put(almond.getId(), almond); //menambahkan data pada hashmap
    }
    
    //Mengambil data
    @RequestMapping(value = "/products") //tampilan halaman products
    public ResponseEntity<Object> getProduct() {
        return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);  //menampilkan seluruh data pada hashmap dengan function values()
    }
    
    //Create, input data
    @RequestMapping(value = "/products", method = RequestMethod.POST) //tampilan halaman products dengan penambahan request POST untuk menambahkan data
    public ResponseEntity<Object> createProduct(@RequestBody Product product /*mengambil data pada halaman menjadi variabel product*/) {
        
        //mengecek dengan if condition dengan penambahan data jika dalam hashmap tidak terdapat id yang sama dengan yang akan ditambahkan
        if (!productRepo.containsKey(product.getId())){ //menggunakan function containsKey() untuk membandingkan data dalam hashmap dengan variable product
            
            int total = product.getPrice() - (product.getPrice() * product.getDiscount()/100);
            
            product.setTotal(total);
            productRepo.put(product.getId(), product); //menambahkan data pada hashmap
            return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED); //menampilkan pesan data telah dibuat dengan sukses
           
        }
        else{
            return new ResponseEntity<>("id already used in another data", HttpStatus.OK); //menampilkan pesan jika id pada variabel product telah terdapat pada hashmap
        }
         
        
    }
    
    //Edit, Mengubah data
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT) //tampilan halaman products beserta id dengan penambahan request PUT untuk mengubah data
    public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody Product product /*mengambil data pada halaman untuk id dan variabel product*/) {
        
        //melakukan perubahan jika hashmap memiliki id yang cocok dengan yang dimasukan pada url/id
        if(productRepo.containsKey(id)){ //menggunakan function containsKey() untuk membandingkan data dalam hashmap dengan variable id
        
            productRepo.remove(id); //menghapus data yang sudah ada sebelumnya
            product.setId(id); //set id baru
            productRepo.put(id, product); //menambahkan data pada hashmap
            return new ResponseEntity<>("Product is updated successfully", HttpStatus.OK); //menampilkan pesan berhasil

        }
        else{
            return new ResponseEntity<>("data id doesn't exists", HttpStatus.OK); //menampilkan pesan jika id tidak ditemukan
        }
        
        
    }
    
    //Hapus data
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE) //tampilan halaman products beserta id dengan penambahan request DELETE untuk menghapus data
    public ResponseEntity<Object> delete(@PathVariable("id") String id /*Mengambil data id pada url*/){
        productRepo.remove(id); //menghapus data
        return new ResponseEntity<>("Product is deleted seuccessfully", HttpStatus.OK); //menampilkan pesan telah terhapus
    }
    
//    @RequestMapping(value = "/products/total", method = RequestMethod.GET)
//    public ResponseEntity<Object> getTotal(@RequestBody Product product) {
//        int harga = product.getPrice();
//        int diskon = product.getDiscount();
//        
//        int total = harga - (harga*diskon/100);
//        
//        
//        
//        
//        return new ResponseEntity<>(total, HttpStatus.OK);  //menampilkan seluruh data pada hashmap dengan function values()
//    }
 }
 