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
    this.loadToDos();
  }

  loadToDos() {
    this.toDoService.get().subscribe((toDos) => {
      this.toDos = toDos;
    });
  }

  onEdit(index: number): void {
    console.log(`Editing to do ${this.toDos[index].id}`);
    this.activeToDoIndex = index;
  }

  onDelete(index: number): void {
    const id = this.toDos[index].id!;
    console.log(`Deleting to do ${id}`);
    this.toDoService.delete(id).subscribe(() => {
      this.loadToDos();
    });
  }

  onSave(toDo: ToDo): void {
    console.log(`Saving to do ${toDo.id}`);
    this.toDoService.update(toDo).subscribe(() => {
      this.activeToDoIndex = undefined;
    });
  }

  onCancel(): void {
    this.activeToDoIndex = undefined;
  }

  onAdd(): void {
    this.toDoService.create().subscribe((newToDo) => {
      this.toDos.push(newToDo);
      this.activeToDoIndex = this.toDos.length - 1;
    });
  }
}
