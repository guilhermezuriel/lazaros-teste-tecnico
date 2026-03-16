import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Profile } from '../../../../models/profile.model';
import { ProfileService } from '../../../../services/profile.service';
import { ProfileFormComponent } from '../profile-form/profile-form.component';

@Component({
  selector: 'app-profile-list',
  templateUrl: './profile-list.component.html',
  styleUrls: ['./profile-list.component.scss']
})
export class ProfileListComponent implements OnInit {
  profiles: Profile[] = [];
  displayedColumns: string[] = ['descricao', 'actions'];
  isLoading = true;

  constructor(
    private profileService: ProfileService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private router: Router
  ) { }

  ngOnInit() {
    this.loadProfiles();
  }

  loadProfiles(): void {
    this.isLoading = true;
    this.profileService.getAll().subscribe({
      next: (profiles) => {
        this.profiles = profiles;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Erro ao carregar perfis:', error);
        this.snackBar.open('Erro ao carregar perfis', 'Fechar', { duration: 3000 });
        this.isLoading = false;
      }
    });
  }

  openCreateDialog(): void {
    const dialogRef = this.dialog.open(ProfileFormComponent, {
      width: '500px',
      data: null
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadProfiles();
      }
    });
  }

  openEditDialog(profile: Profile): void {
    const dialogRef = this.dialog.open(ProfileFormComponent, {
      width: '500px',
      data: profile
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadProfiles();
      }
    });
  }

  deleteProfile(profile: Profile): void {
    if (confirm(`Deseja realmente excluir o perfil "${profile.descricao}"?`)) {
      this.profileService.delete(profile.id).subscribe({
        next: () => {
          this.snackBar.open('Perfil excluído com sucesso', 'Fechar', { duration: 3000 });
          this.loadProfiles();
        },
        error: (error) => {
          console.error('Erro ao excluir perfil:', error);
          this.snackBar.open('Erro ao excluir perfil. Pode estar associado a usuários.', 'Fechar', { duration: 5000 });
        }
      });
    }
  }
}
