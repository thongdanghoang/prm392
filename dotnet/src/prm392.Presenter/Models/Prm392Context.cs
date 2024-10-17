using Microsoft.EntityFrameworkCore;

namespace prm392.Presenter.Models;

public partial class Prm392Context : DbContext
{
    public Prm392Context()
    {
    }

    public Prm392Context(DbContextOptions<Prm392Context> options)
        : base(options)
    {
    }

    public virtual DbSet<Authority> Authorities { get; set; }

    public virtual DbSet<MenuItem> MenuItems { get; set; }

    public virtual DbSet<Notification> Notifications { get; set; }

    public virtual DbSet<Reservation> Reservations { get; set; }

    public virtual DbSet<ReservationMenuItem> ReservationMenuItems { get; set; }

    public virtual DbSet<Seat> Seats { get; set; }

    public virtual DbSet<Transaction> Transactions { get; set; }

    public virtual DbSet<User> Users { get; set; }

    public virtual DbSet<UserNotificationPreference> UserNotificationPreferences { get; set; }

    public virtual DbSet<UsersAuthority> UsersAuthorities { get; set; }

//    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
//#warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see https://go.microsoft.com/fwlink/?LinkId=723263.
//        => optionsBuilder.UseNpgsql("Server=localhost;Port=5432;Database=prm392;Uid=postgres;Password=postgres");

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Authority>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("authorities_pkey");

            entity.ToTable("authorities");

            entity.HasIndex(e => e.Role, "authorities_role_key").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedNever()
                .HasColumnName("id");
            entity.Property(e => e.Role)
                .HasMaxLength(255)
                .HasColumnName("role");
        });

        modelBuilder.Entity<MenuItem>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("menu_items_pkey");

            entity.ToTable("menu_items");

            entity.HasIndex(e => e.Name, "menu_items_name_key").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedNever()
                .HasColumnName("id");
            entity.Property(e => e.Category)
                .HasMaxLength(50)
                .HasComment(" (e.g., starter, main_course, dessert)")
                .HasColumnName("category");
            entity.Property(e => e.Description)
                .HasMaxLength(255)
                .HasColumnName("description");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
            entity.Property(e => e.Price).HasColumnName("price");
        });

        modelBuilder.Entity<Notification>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("notifications_pkey");

            entity.ToTable("notifications");

            entity.Property(e => e.Id)
                .ValueGeneratedNever()
                .HasColumnName("id");
            entity.Property(e => e.UserId).HasColumnName("user_id");
            entity.Property(e => e.Version).HasColumnName("version");

            entity.HasOne(d => d.User).WithMany(p => p.Notifications)
                .HasForeignKey(d => d.UserId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("notifications_user_id_fkey");
        });

        modelBuilder.Entity<Reservation>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("reservations_pkey");

            entity.ToTable("reservations");

            entity.Property(e => e.Id)
                .ValueGeneratedNever()
                .HasColumnName("id");
            entity.Property(e => e.CreatedBy)
                .HasMaxLength(255)
                .HasColumnName("created_by");
            entity.Property(e => e.CreatedDate)
                .HasColumnType("timestamp without time zone")
                .HasColumnName("created_date");
            entity.Property(e => e.LastModifiedBy)
                .HasMaxLength(255)
                .HasColumnName("last_modified_by");
            entity.Property(e => e.LastModifiedDate)
                .HasColumnType("timestamp without time zone")
                .HasColumnName("last_modified_date");
            entity.Property(e => e.NumberOfGuests)
                .ValueGeneratedOnAdd()
                .HasColumnName("number_of_guests");
            entity.Property(e => e.ReservationDate).HasColumnName("reservation_date");
            entity.Property(e => e.SeatId).HasColumnName("seat_id");
            entity.Property(e => e.Status)
                .HasMaxLength(50)
                .HasComment("(e.g., confirmed, cancelled)")
                .HasColumnName("status");
            entity.Property(e => e.TimeSlotFromInclusive).HasColumnName("time_slot_from_inclusive");
            entity.Property(e => e.TimeSlotToExclusive).HasColumnName("time_slot_to_exclusive");
            entity.Property(e => e.TransactionId).HasColumnName("transaction_id");
            entity.Property(e => e.UserId).HasColumnName("user_id");
            entity.Property(e => e.Version).HasColumnName("version");

            entity.HasOne(d => d.Seat).WithMany(p => p.Reservations)
                .HasForeignKey(d => d.SeatId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("reservations_seat_id_fkey");

            entity.HasOne(d => d.User).WithMany(p => p.Reservations)
                .HasForeignKey(d => d.UserId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("reservations_user_id_fkey");
        });

        modelBuilder.Entity<ReservationMenuItem>(entity =>
        {
            entity.HasKey(e => new { e.ReservationId, e.MenuItemId }).HasName("reservation_menu_items_pkey");

            entity.ToTable("reservation_menu_items");

            entity.HasIndex(e => e.ReservationId, "reservation_menu_items_reservation_id_key").IsUnique();

            entity.Property(e => e.ReservationId).HasColumnName("reservation_id");
            entity.Property(e => e.MenuItemId).HasColumnName("menu_item_id");
            entity.Property(e => e.Quantity)
                .ValueGeneratedOnAdd()
                .HasColumnName("quantity");

            entity.HasOne(d => d.MenuItem).WithMany(p => p.ReservationMenuItems)
                .HasForeignKey(d => d.MenuItemId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("reservation_menu_items_menu_item_id_fkey");

            entity.HasOne(d => d.Reservation).WithOne(p => p.ReservationMenuItem)
                .HasForeignKey<ReservationMenuItem>(d => d.ReservationId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("reservation_menu_items_reservation_id_fkey");
        });

        modelBuilder.Entity<Seat>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("seats_pkey");

            entity.ToTable("seats");

            entity.Property(e => e.Id)
                .ValueGeneratedNever()
                .HasColumnName("id");
            entity.Property(e => e.Capacity)
                .ValueGeneratedOnAdd()
                .HasColumnName("capacity");
            entity.Property(e => e.FloorNumber)
                .ValueGeneratedOnAdd()
                .HasColumnName("floor_number");
            entity.Property(e => e.Name)
                .HasMaxLength(255)
                .HasColumnName("name");
            entity.Property(e => e.Type)
                .HasMaxLength(255)
                .HasComment("(e.g., window, booth, outdoor)")
                .HasColumnName("type");
        });

        modelBuilder.Entity<Transaction>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("transactions_pkey");

            entity.ToTable("transactions");

            entity.Property(e => e.Id)
                .ValueGeneratedNever()
                .HasColumnName("id");
            entity.Property(e => e.Amount).HasColumnName("amount");
            entity.Property(e => e.CreatedBy)
                .HasMaxLength(255)
                .HasColumnName("created_by");
            entity.Property(e => e.CreatedDate)
                .HasColumnType("timestamp without time zone")
                .HasColumnName("created_date");
            entity.Property(e => e.LastModifiedBy)
                .HasMaxLength(255)
                .HasColumnName("last_modified_by");
            entity.Property(e => e.LastModifiedDate)
                .HasColumnType("timestamp without time zone")
                .HasColumnName("last_modified_date");
            entity.Property(e => e.PaymentMethod)
                .HasMaxLength(50)
                .HasColumnName("payment_method");
            entity.Property(e => e.Status)
                .HasMaxLength(50)
                .HasColumnName("status");
            entity.Property(e => e.TransactionType)
                .HasMaxLength(50)
                .HasColumnName("transaction_type");
            entity.Property(e => e.Version).HasColumnName("version");
        });

        modelBuilder.Entity<User>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("users_pkey");

            entity.ToTable("users");

            entity.HasIndex(e => e.Email, "users_email_key").IsUnique();

            entity.HasIndex(e => e.Phone, "users_phone_key").IsUnique();

            entity.HasIndex(e => e.Username, "users_username_key").IsUnique();

            entity.Property(e => e.Id)
                .ValueGeneratedNever()
                .HasColumnName("id");
            entity.Property(e => e.Avatar)
                .HasMaxLength(255)
                .HasColumnName("avatar");
            entity.Property(e => e.Birthday).HasColumnName("birthday");
            entity.Property(e => e.CreatedBy)
                .HasMaxLength(255)
                .HasColumnName("created_by");
            entity.Property(e => e.CreatedDate)
                .HasColumnType("timestamp without time zone")
                .HasColumnName("created_date");
            entity.Property(e => e.Email)
                .HasMaxLength(255)
                .HasColumnName("email");
            entity.Property(e => e.FirstName)
                .HasMaxLength(255)
                .HasColumnName("first_name");
            entity.Property(e => e.LastModifiedBy)
                .HasMaxLength(255)
                .HasColumnName("last_modified_by");
            entity.Property(e => e.LastModifiedDate)
                .HasColumnType("timestamp without time zone")
                .HasColumnName("last_modified_date");
            entity.Property(e => e.LastName)
                .HasMaxLength(255)
                .HasColumnName("last_name");
            entity.Property(e => e.Password)
                .HasMaxLength(255)
                .HasColumnName("password");
            entity.Property(e => e.Phone)
                .HasMaxLength(50)
                .HasColumnName("phone");
            entity.Property(e => e.Username)
                .HasMaxLength(255)
                .HasColumnName("username");
            entity.Property(e => e.Version).HasColumnName("version");
        });

        modelBuilder.Entity<UserNotificationPreference>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("user_notification_preferences_pkey");

            entity.ToTable("user_notification_preferences");

            entity.Property(e => e.Id)
                .ValueGeneratedNever()
                .HasColumnName("id");
            entity.Property(e => e.Enabled).HasColumnName("enabled");
            entity.Property(e => e.Frequency)
                .HasMaxLength(50)
                .HasColumnName("frequency");
            entity.Property(e => e.NotificationType)
                .HasMaxLength(50)
                .HasColumnName("notification_type");
            entity.Property(e => e.UserId).HasColumnName("user_id");
            entity.Property(e => e.Version).HasColumnName("version");

            entity.HasOne(d => d.User).WithMany(p => p.UserNotificationPreferences)
                .HasForeignKey(d => d.UserId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("user_notification_preferences_user_id_fkey");
        });

        modelBuilder.Entity<UsersAuthority>(entity =>
        {
            entity.HasKey(e => new { e.UserId, e.AuthorityId }).HasName("users_authorities_pkey");

            entity.ToTable("users_authorities");

            entity.HasIndex(e => e.UserId, "users_authorities_user_id_key").IsUnique();

            entity.Property(e => e.UserId).HasColumnName("user_id");
            entity.Property(e => e.AuthorityId).HasColumnName("authority_id");

            entity.HasOne(d => d.Authority).WithMany(p => p.UsersAuthorities)
                .HasForeignKey(d => d.AuthorityId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("users_authorities_authority_id_fkey");

            entity.HasOne(d => d.User).WithOne(p => p.UsersAuthority)
                .HasForeignKey<UsersAuthority>(d => d.UserId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("users_authorities_user_id_fkey");
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
