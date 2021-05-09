import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ToDo } from '../to-do';

@Component({
  selector: 'app-to-do-card',
  templateUrl: './to-do-card.component.html',
  styleUrls: ['./to-do-card.component.css'],
})
export class ToDoCardComponent {
  @Input() toDo: ToDo = { title: '', description: '', done: false };
  @Input() editable: boolean = false;
  @Input() active: boolean = false;

  @Output() edit = new EventEmitter();
  @Output() save = new EventEmitter<ToDo>();

  onEdit() {
    this.edit.emit(null);
  }

  onSave() {
    this.save.emit(this.toDo);
  }
}
