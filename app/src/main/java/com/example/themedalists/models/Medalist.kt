package com.example.themedalists.models
import android.os.Parcel
import android.os.Parcelable

data class Medalist(
    val country: String?,
    val iocCode: String?,
    val timesCompeted: Int,
    val goldMedals: Int,
    val silverMedals: Int,
    val bronzeMedals: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(country)
        parcel.writeString(iocCode)
        parcel.writeInt(timesCompeted)
        parcel.writeInt(goldMedals)
        parcel.writeInt(silverMedals)
        parcel.writeInt(bronzeMedals)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Medalist> {
        override fun createFromParcel(parcel: Parcel): Medalist {
            return Medalist(parcel)
        }

        override fun newArray(size: Int): Array<Medalist?> {
            return arrayOfNulls(size)
        }
    }
}


