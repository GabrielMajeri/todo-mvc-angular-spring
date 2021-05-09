import { Injectable } from '@angular/core';
import { ToDo } from './to-do';

@Injectable({
  providedIn: 'root',
})
export class ToDoService {
  getToDos(): ToDo[] {
    return [
      {
        id: 1,
        title: 'Do something',
        description: 'You must do something',
        done: false,
      },
      { id: 2, title: 'Refactor code', description: '', done: true },
      {
        id: 3,
        title: 'Finish homework',
        description: 'It has to be done',
        done: false,
      },
    ];
  }
}
