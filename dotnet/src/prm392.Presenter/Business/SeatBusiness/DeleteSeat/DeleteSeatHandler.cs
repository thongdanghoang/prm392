namespace prm392.Presenter.Business.SeatBusiness.DeleteSeat;

public record DeleteSeatCommand(Guid Id) : ICommand<DeleteSeatResult>;
public record DeleteSeatResult(bool IsSuccess);
public class DeleteSeatHandler(Prm392Context _db)
    : ICommandHandler<DeleteSeatCommand, DeleteSeatResult>
{
    public async Task<DeleteSeatResult> Handle(DeleteSeatCommand request, CancellationToken cancellationToken)
    {
        var seat = await _db.Seats.FindAsync(request.Id);
        if (seat == null)
            throw new Exception("Can not find seat to delete!");
        _db.Seats.Remove(seat); 
        await _db.SaveChangesAsync();
        return new DeleteSeatResult(true);
    }
}
