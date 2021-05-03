import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ToDo } from '../to-do';

@Component({
  selector: 'app-to-do-card',
  templateUrl: './to-do-card.component.html',
  styleUrls: ['./to-do-card.component.css'],
})
export class ToDoCardComponent {
  @Input() toDo: ToDo = { name: '', description: '', done: false };
  @Output() edit = new EventEmitter();

  toggleDone() {
    this.toDo.done = !this.toDo.done;
  }

  onEdit() {
    this.edit.emit(null);
  }
}
