package com.example.mappers

import com.example.Mapper
import com.example.database.entities.DBMemberClassEntity
import com.example.models.StudentClasses

class MemberClassMapper: Mapper<DBMemberClassEntity,StudentClasses> {
    override fun map(input: DBMemberClassEntity): StudentClasses =
        StudentClasses(
            classId = input.classId.id,
            joinDate = input.dateJoined
        )

}