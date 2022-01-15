import { Route } from '@angular/compiler/src/core';
import {Component, ElementRef, OnInit, QueryList, ViewChildren} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs';
import { User } from 'src/app/Model/User';
import { UserRestService } from 'src/app/services/rest/user/user.rest.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-users-admin',
  templateUrl: './users-admin.component.html',
  styleUrls: ['./users-admin.component.css']
})
export class UsersAdminComponent implements OnInit {

  users!: User[];
  addNewUser = false;
  showInfo = false;
  disableDelete = false;
  selectedUser!: User;
  selectedUsers: User[] = [];
  @ViewChildren("checkboxes")
  checkboxes!: QueryList<ElementRef>;

  constructor(private userService: UserRestService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.users = this.route.snapshot.data['users'];
  }

  uncheckAll() {
    this.checkboxes.forEach((element) => {
      element.nativeElement.checked = false;
    });
  }

  fetchUsers() {
    this.userService.getAllUsers().subscribe(
      data =>  {
        setTimeout(() => {}, 500);
        this.users = data;
      }
    ),
    (error: any) => (
      console.log(error)
    )
  }

  deleteUser() {
    if (this.selectedUsers.length > 0) {
      this.userService.deleteUsers(this.selectedUsers[0]);
      this.selectedUsers.pop();
    }
    this.uncheckAll();
    this.fetchUsers();
  }

  closeAddUserComp() {
    this.addNewUser = false;
    this.router.navigate(['/admin/users']);
    this.fetchUsers();
  }

  openAddUserComp() {
    this.addNewUser = true;
  }

  selectUser(user: User) {
    this.selectedUser = user;
  }

  selectUsers(user: User) {
    if(this.selectedUsers.includes(user)) {
      this.selectedUsers.splice(this.selectedUsers.indexOf(user), 1);
    } else {
      this.selectedUsers.push(user)
    }

    if (this.selectedUsers.length > 1) {
      this.disableDelete = true;
    } else {
      this.disableDelete = false;
    }
  }

  openInfoUserComp(user: User) {
    this.selectUser(user);
    this.showInfo = true;
  }

  closeInfoUserComp() {
    this.showInfo = false;
    this.router.navigate(['/admin/users']);
  }

}
