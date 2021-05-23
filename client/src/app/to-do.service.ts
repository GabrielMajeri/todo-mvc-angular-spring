import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ToDo } from './to-do';

@Injectable({
  providedIn: 'root',
})
export class ToDoService {
  constructor(private http: HttpClient) {}

  get(): Observable<ToDo[]> {
    return this.http.get<ToDo[]>('/api/todos');
  }

  create(): Observable<ToDo> {
    return this.http.post<ToDo>('/api/todos', { title: '' });
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`/api/todos/${id}`);
  }

  update(toDo: ToDo): Observable<any> {
    return this.http.post(`/api/todos`, toDo);
  }
}
