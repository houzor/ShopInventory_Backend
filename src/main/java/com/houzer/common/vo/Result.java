package com.houzer.common.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//这里用泛型，是因为返回的数据可能是User，也可能是List<User>
public class Result <T>{
    private Integer code;
    private String message;
    private T data;

    //定义成功的静态方法
    public static <T> Result<T> success(){
        return new Result<>(20000,"success",null);
    }

    public static <T> Result<T> success(T data){
        return new Result<>(20000,"success",data);
    }

    public static <T> Result<T> success(T data,String message){
        return new Result<>(20000,message,data);
    }

    public static <T> Result<T> success(String message){
        return new Result<>(20000,message,null);
    }

    //定义失败的静态方法
    public static<T>  Result<T> fail(){
        return new Result<>(20001,"fail",null);
    }

    public static<T>  Result<T> fail(Integer code){
        return new Result<>(code,"fail",null);
    }

    public static<T>  Result<T> fail(Integer code, String message){
        return new Result<>(code,message,null);
    }

    public static<T>  Result<T> fail( String message){
        return new Result<>(20001,message,null);
    }
}
