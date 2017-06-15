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

import com.example.getstarted.objects.Book;
import com.example.getstarted.objects.Result;

import com.example.getstarted.objects.Schedule;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultIterator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// [START example]
public class DatastoreDao implements BookDao, ScheduleDao {

  // [START constructor]
  private DatastoreService datastore;
  private static final String BOOK_KIND = "Book";
  private static final String SCHEDULE_KIND = "Schedule";

  public DatastoreDao() {
    datastore = DatastoreServiceFactory.getDatastoreService(); // Authorized Datastore service
  }
  // [END constructor]
  // [START entityToBook]
  public Book entityToBook(Entity entity) {
    return new Book.Builder()                                     // Convert to Book form
        .author((String)entity.getProperty(Book.AUTHOR))
        .description((String)entity.getProperty(Book.DESCRIPTION))
        .id(entity.getKey().getId())
        .publishedDate((String)entity.getProperty(Book.PUBLISHED_DATE))
        .title((String)entity.getProperty(Book.TITLE))
        .build();
  }
  // [END entityToBook]
  // [START create]
  @Override
  public Long createBook(Book book) {
    Entity incBookEntity = new Entity(BOOK_KIND);  // Key will be assigned once written
    incBookEntity.setProperty(Book.AUTHOR, book.getAuthor());
    incBookEntity.setProperty(Book.DESCRIPTION, book.getDescription());
    incBookEntity.setProperty(Book.PUBLISHED_DATE, book.getPublishedDate());
    incBookEntity.setProperty(Book.TITLE, book.getTitle());

    Key bookKey = datastore.put(incBookEntity); // Save the Entity
    return bookKey.getId();                     // The ID of the Key
  }
  // [END create]
  // [START read]
  @Override
  public Book readBook(Long bookId) {
    try {
      Entity bookEntity = datastore.get(KeyFactory.createKey(BOOK_KIND, bookId));
      return entityToBook(bookEntity);
    } catch (EntityNotFoundException e) {
      return null;
    }
  }
  // [END read]
  // [START update]
  @Override
  public void updateBook(Book book) {
    Key key = KeyFactory.createKey(BOOK_KIND, book.getId());  // From a book, create a Key
    Entity entity = new Entity(key);         // Convert Book to an Entity
    entity.setProperty(Book.AUTHOR, book.getAuthor());
    entity.setProperty(Book.DESCRIPTION, book.getDescription());
    entity.setProperty(Book.PUBLISHED_DATE, book.getPublishedDate());
    entity.setProperty(Book.TITLE, book.getTitle());

    datastore.put(entity);                   // Update the Entity
  }
  // [END update]
  // [START delete]
  @Override
  public void deleteBook(Long bookId) {
    Key key = KeyFactory.createKey(BOOK_KIND, bookId);        // Create the Key
    datastore.delete(key);                      // Delete the Entity
  }
  // [END delete]
  // [START entitiesToBooks]
  public List<Book> entitiesToBooks(Iterator<Entity> results) {
    List<Book> resultBooks = new ArrayList<>();
    while (results.hasNext()) {  // We still have data
      resultBooks.add(entityToBook(results.next()));      // Add the Book to the List
    }
    return resultBooks;
  }
  // [END entitiesToBooks]
  // [START listbooks]
  @Override
  public Result<Book> listBooks(String startCursorString) {
    FetchOptions fetchOptions = FetchOptions.Builder.withLimit(10); // Only show 10 at a time
    if (startCursorString != null && !startCursorString.equals("")) {
      fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
    }
    Query query = new Query(BOOK_KIND) // We only care about Books
        .addSort(Book.TITLE, SortDirection.ASCENDING); // Use default Index "title"
    PreparedQuery preparedQuery = datastore.prepare(query);
    QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

    List<Book> resultBooks = entitiesToBooks(results);     // Retrieve and convert Entities
    Cursor cursor = results.getCursor();              // Where to start next time
    if (cursor != null && resultBooks.size() == 10) {         // Are we paging? Save Cursor
      String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
      return new Result<>(resultBooks, cursorString);
    } else {
      return new Result<>(resultBooks);
    }
  }
  // [END listbooks]

  public Schedule entityToSchedule(Entity entity) {
    return new Schedule.Builder()                                     // Convert to Schedule form
        .author((String)entity.getProperty(Schedule.AUTHOR))
        .description((String)entity.getProperty(Schedule.DESCRIPTION))
        .id(entity.getKey().getId())
        .publishedDate((String)entity.getProperty(Schedule.PUBLISHED_DATE))
        .title((String)entity.getProperty(Schedule.TITLE))
        .build();
  }
  // [END entityToSchedule]
  // [START create]
  @Override
  public Long createSchedule(Schedule schedule) {
    Entity incScheduleEntity = new Entity(SCHEDULE_KIND);  // Key will be assigned once written
    incScheduleEntity.setProperty(Schedule.AUTHOR, schedule.getAuthor());
    incScheduleEntity.setProperty(Schedule.DESCRIPTION, schedule.getDescription());
    incScheduleEntity.setProperty(Schedule.PUBLISHED_DATE, schedule.getPublishedDate());
    incScheduleEntity.setProperty(Schedule.TITLE, schedule.getTitle());

    Key scheduleKey = datastore.put(incScheduleEntity); // Save the Entity
    return scheduleKey.getId();                     // The ID of the Key
  }
  // [END create]
  // [START read]
  @Override
  public Schedule readSchedule(Long scheduleId) {
    try {
      Entity scheduleEntity = datastore.get(KeyFactory.createKey(SCHEDULE_KIND, scheduleId));
      return entityToSchedule(scheduleEntity);
    } catch (EntityNotFoundException e) {
      return null;
    }
  }
  // [END read]
  // [START update]
  @Override
  public void updateSchedule(Schedule schedule) {
    Key key = KeyFactory.createKey(SCHEDULE_KIND, schedule.getId());  // From a schedule, create a Key
    Entity entity = new Entity(key);         // Convert Schedule to an Entity
    entity.setProperty(Schedule.AUTHOR, schedule.getAuthor());
    entity.setProperty(Schedule.DESCRIPTION, schedule.getDescription());
    entity.setProperty(Schedule.PUBLISHED_DATE, schedule.getPublishedDate());
    entity.setProperty(Schedule.TITLE, schedule.getTitle());

    datastore.put(entity);                   // Update the Entity
  }
  // [END update]
  // [START delete]
  @Override
  public void deleteSchedule(Long scheduleId) {
    Key key = KeyFactory.createKey(SCHEDULE_KIND, scheduleId);        // Create the Key
    datastore.delete(key);                      // Delete the Entity
  }
  // [END delete]
  // [START entitiesToSchedules]
  public List<Schedule> entitiesToSchedules(Iterator<Entity> results) {
    List<Schedule> resultSchedules = new ArrayList<>();
    while (results.hasNext()) {  // We still have data
      resultSchedules.add(entityToSchedule(results.next()));      // Add the Schedule to the List
    }
    return resultSchedules;
  }
  // [END entitiesToSchedules]
  // [START listschedules]
  @Override
  public Result<Schedule> listSchedules(String startCursorString) {
    FetchOptions fetchOptions = FetchOptions.Builder.withLimit(10); // Only show 10 at a time
    if (startCursorString != null && !startCursorString.equals("")) {
      fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
    }
    Query query = new Query(SCHEDULE_KIND) // We only care about Schedules
        .addSort(Schedule.TITLE, SortDirection.ASCENDING); // Use default Index "title"
    PreparedQuery preparedQuery = datastore.prepare(query);
    QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

    List<Schedule> resultSchedules = entitiesToSchedules(results);     // Retrieve and convert Entities
    Cursor cursor = results.getCursor();              // Where to start next time
    if (cursor != null && resultSchedules.size() == 10) {         // Are we paging? Save Cursor
      String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
      return new Result<>(resultSchedules, cursorString);
    } else {
      return new Result<>(resultSchedules);
    }
  }
  // [END listschedules]
}
// [END example]
