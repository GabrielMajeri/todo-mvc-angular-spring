import { Component, OnInit } from '@angular/core';
import { ToDo } from '../to-do';
import { ToDoService } from '../to-do.service';

@Component({
  selector: 'app-to-do-list',
  templateUrl: './to-do-list.component.html',
  styleUrls: ['./to-do-list.component.css'],
})
export class ToDoListComponent implements OnInit {
  toDos: ToDo[] = [];
  activeToDoIndex?: number;

  constructor(private toDoService: ToDoService) {}

  ngOnInit(): void {
    this.toDos = this.toDoService.getToDos();
  }

  onEdit(index: number): void {
    console.log(`Editing to do ${this.toDos[index].id}`);
    this.activeToDoIndex = index;
  }

  onDelete(index: number): void {
    console.log(`Deleting to do ${this.toDos[index].id}`);
    this.toDos.splice(index, 1);
  }

  onSave(toDo: ToDo): void {
    console.log(`Saving to do ${toDo.id}`);
    this.activeToDoIndex = undefined;
  }

  onCancel(): void {
    this.activeToDoIndex = undefined;
  }
}
