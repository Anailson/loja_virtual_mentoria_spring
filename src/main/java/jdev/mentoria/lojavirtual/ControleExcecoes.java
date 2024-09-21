package jdev.mentoria.lojavirtual;

import jdev.mentoria.lojavirtual.model.dto.ObjetoErroDTO;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.List;

@RestControllerAdvice
@ControllerAdvice
public class ControleExcecoes  extends ResponseEntityExceptionHandler {

    //captura exceções do projeto
    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

        String msg = "";

        if(ex instanceof MethodArgumentNotValidException){  //MethodArgumentNotValidException -> erro qd passamos uma requisição errada na API
            List<ObjectError> list =((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();

            for(ObjectError objectError : list) {
                msg += objectError.getDefaultMessage() + "\n";
            }
          }else{
                msg = ex.getMessage();
            }
            objetoErroDTO.setError(msg);
            objetoErroDTO.setCode(status.value() + " ==> " + status.getReasonPhrase());

            return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        //metodo para captura bando de dados
     @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
     protected  ResponseEntity<Object> handleExceptionDataIntegry( Exception ex) {


         ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

         String msg = "";

         if (ex instanceof DataIntegrityViolationException) {
             msg = "Erro de integridade no banco: " + ((DataIntegrityViolationException) ex).getCause().getMessage();
         } else
         if (ex instanceof ConstraintViolationException){
             msg = "Erro de cahve estrangeira: " + ((ConstraintViolationException) ex).getCause().getMessage();
         } else
         if (ex instanceof SQLException) {
             msg = "Erro de SQL do banco: " + ((SQLException) ex).getCause().getMessage();
         } else {
             msg = ex.getMessage();
         }

         objetoErroDTO.setError(msg);
         objetoErroDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());

         return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);

         }

}


/**
 * Responsável por intercepta as as exceções - erros do servidor por exemplo
 */