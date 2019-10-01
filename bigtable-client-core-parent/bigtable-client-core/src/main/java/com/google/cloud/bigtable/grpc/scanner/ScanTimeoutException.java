/*
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.cloud.bigtable.grpc.scanner;

import com.google.api.core.InternalApi;
import java.io.IOException;

/**
 * An IOException that presents timeout when reading response.
 *
 * <p>For internal use only - public for technical reasons.
 */
@InternalApi("For internal usage only")
@Deprecated
public class ScanTimeoutException extends IOException {

  private static final long serialVersionUID = 4115316291347038875L;

  /**
   * Constructor for ScanTimeoutException.
   *
   * @param message a {@link java.lang.String} object.
   */
  public ScanTimeoutException(String message) {
    super(message);
  }
}
