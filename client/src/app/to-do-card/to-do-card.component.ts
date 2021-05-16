import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
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
  @Output() delete = new EventEmitter();
  @Output() save = new EventEmitter<ToDo>();

  constructor(private dialog: MatDialog) {}

  onEdit() {
    this.edit.emit(null);
  }

  onDelete() {
    const dialogRef = this.dialog.open(ToDoCardDeleteDialog, {});

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.delete.emit(null);
      }
    });
  }

  onSave() {
    this.save.emit(this.toDo);
  }
}

@Component({
  selector: 'app-to-do-card-delete-dialog',
  templateUrl: './to-do-card-delete-dialog.html',
})
export class ToDoCardDeleteDialog {}
