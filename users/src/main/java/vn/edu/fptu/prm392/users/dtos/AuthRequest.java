package vn.edu.fptu.prm392.users.dtos;

public record AuthRequest(
        String username,
        String password
) {
}
