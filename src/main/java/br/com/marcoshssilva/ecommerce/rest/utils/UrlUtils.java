package br.com.marcoshssilva.ecommerce.rest.utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UrlUtils {
    
        public static List<Integer> decodeIntList(String l) {
            return Arrays.asList(
                    l.split(",")).stream().map( i -> Integer.parseInt(i)).collect(Collectors.toList());
        }
        
        
        public static String decodeParam(String r) {
            return URLDecoder.decode(r, StandardCharsets.UTF_8);
        }
}
