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
        name: 'Do something',
        description: 'You must do something',
        done: false,
      },
      { id: 2, name: 'Refactor code', description: '', done: true },
      {
        id: 3,
        name: 'Finish homework',
        description: 'It has to be done',
        done: false,
      },
    ];
  }
}
