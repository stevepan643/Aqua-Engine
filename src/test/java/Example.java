/*
 * Copyright 2024 Steve Pan
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;

public class Example {
  public static void exampleFunction(@Nonnull Integer... args) {
    for (Integer arg : args) {
      System.out.println(arg);
    }
  }

  public static void main(String[] args) {
    List<Integer> myList = Arrays.asList(1, 2, 3, 4);
    exampleFunction(myList.toArray(Integer[]::new));
  }
}
