package br.com.marcoshssilva.ecommerce.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
    
        public static List<Integer> decodeIntList(String l) {
            return Arrays.asList(
                    l.split(",")).stream().map( i -> Integer.parseInt(i)).collect(Collectors.toList());
        }
        
        
        public static String decodeParam(String r) {
            try {
                return URLDecoder.decode(r, "utf-8");
            } catch (UnsupportedEncodingException ex) {
                return "";
            }
        }
}
