package com.ivscheianu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
final class Parcel {
    private int rowIndex;
    private int columnIndex;
    private long type;
}
