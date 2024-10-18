using System;
using System.Collections.Generic;

namespace prm392.Presenter.Models;

public partial class Transaction
{
    public Guid Id { get; set; }

    public int Version { get; set; }

    public string CreatedBy { get; set; } = null!;

    public DateTime CreatedDate { get; set; }

    public string LastModifiedBy { get; set; } = null!;

    public DateTime LastModifiedDate { get; set; }

    public long Amount { get; set; }

    public string Status { get; set; } = null!;

    public string TransactionType { get; set; } = null!;

    public string PaymentMethod { get; set; } = null!;
}
