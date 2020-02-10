package com.gabia.gyebalja.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonJsonFormat<T> {
   private T code;
   private T message;
   private T response;
}
