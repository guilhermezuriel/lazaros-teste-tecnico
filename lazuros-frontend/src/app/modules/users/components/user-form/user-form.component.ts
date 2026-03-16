import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { User } from '../../../../models/user.model';
import { Profile } from '../../../../models/profile.model';
import { UserService } from '../../../../services/user.service';
import { ProfileService } from '../../../../services/profile.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})
export class UserFormComponent implements OnInit {
  form: FormGroup;
  isEdit = false;
  isSubmitting = false;
  availableProfiles: Profile[] = [];
  isLoadingProfiles = true;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private profileService: ProfileService,
    private dialogRef: MatDialogRef<UserFormComponent>,
    private snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: User
  ) {
    this.isEdit = !!data;
  }

  ngOnInit() {
    this.loadProfiles();
    
    const selectedProfileIds = this.data ? this.data.perfis.map(p => p.id) : [];
    
    this.form = this.fb.group({
      nome: [this.data ? this.data.nome : '', [Validators.required, Validators.minLength(10)]],
      perfis: [selectedProfileIds, [Validators.required]]
    });
  }

  loadProfiles(): void {
    this.profileService.getAll().subscribe({
      next: (profiles) => {
        this.availableProfiles = profiles;
        this.isLoadingProfiles = false;
      },
      error: (error) => {
        console.error('Erro ao carregar perfis:', error);
        this.snackBar.open('Erro ao carregar perfis', 'Fechar', { duration: 3000 });
        this.isLoadingProfiles = false;
      }
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      return;
    }

    this.isSubmitting = true;
    const userData = this.form.value;

    const request = this.isEdit
      ? this.userService.update(this.data.id, userData)
      : this.userService.create(userData);

    request.subscribe({
      next: () => {
        this.snackBar.open(
          this.isEdit ? 'Usuário atualizado com sucesso' : 'Usuário criado com sucesso',
          'Fechar',
          { duration: 3000 }
        );
        this.dialogRef.close(true);
      },
      error: (error) => {
        console.error('Erro ao salvar usuário:', error);
        this.snackBar.open('Erro ao salvar usuário', 'Fechar', { duration: 3000 });
        this.isSubmitting = false;
      }
    });
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }

  getErrorMessage(field: string): string {
    const control = this.form.get(field);
    if (control && control.hasError('required')) {
      return 'Campo obrigatório';
    }
    if (control && control.hasError('minlength')) {
      return 'Mínimo de 10 caracteres';
    }
    return '';
  }
}
