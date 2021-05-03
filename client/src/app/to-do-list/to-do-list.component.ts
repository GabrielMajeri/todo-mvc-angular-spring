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

  constructor(private toDoService: ToDoService) {}

  ngOnInit(): void {
    this.toDos = this.toDoService.getToDos();
  }
}
