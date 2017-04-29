/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.getstarted.daos;

import com.example.getstarted.objects.Schedule;
import com.example.getstarted.objects.Result;

import java.sql.SQLException;

// [START example]
public interface ScheduleDao {
  Long createSchedule(Schedule schedule) throws SQLException;

  Schedule readSchedule(Long scheduleId) throws SQLException;

  void updateSchedule(Schedule schedule) throws SQLException;

  void deleteSchedule(Long scheduleId) throws SQLException;

  Result<Schedule> listSchedules(String startCursor) throws SQLException;
}
// [END example]
