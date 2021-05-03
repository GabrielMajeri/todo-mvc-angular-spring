import { Component, Input } from '@angular/core';
import { ToDo } from '../to-do';

@Component({
  selector: 'app-to-do-card',
  templateUrl: './to-do-card.component.html',
})
export class ToDoCardComponent {
  @Input() toDo: ToDo = { name: '', description: '' };
}
