/*
 * Copyright 2018 Google LLC. All Rights Reserved.
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
package com.google.cloud.bigtable.util;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ThreadFactory;

public class ThreadUtil {

  // AppEngine runtimes have constraints on threading and socket handling
  // that need to be accommodated.
  public static final boolean IS_RESTRICTED_APPENGINE =
          System.getProperty("com.google.appengine.runtime.environment") != null
                  && "1.7".equals(System.getProperty("java.specification.version"));

  /**
   * Get a {@link ThreadFactory} suitable for use in the current environment.
   * @param nameFormat to apply to threads created by the factory.
   * @param daemon {@code true} if the threads the factory creates are daemon threads, {@code false}
   *     otherwise.
   * @return a {@link ThreadFactory}.
   */
  public static ThreadFactory getThreadFactory(String nameFormat, boolean daemon) {
    if (IS_RESTRICTED_APPENGINE) {
      return MoreExecutors.platformThreadFactory();
    } else {
      return new ThreadFactoryBuilder()
          .setDaemon(daemon)
          .setNameFormat(nameFormat)
          .build();
    }
  }

}
