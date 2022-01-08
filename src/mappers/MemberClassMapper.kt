package com.example.mappers

import com.example.Mapper
import com.example.database.entities.MemberClass
import com.example.models.StudentClasses
import org.jetbrains.exposed.sql.selectAll
import java.util.*

class MemberClassMapper: Mapper<MemberClass,StudentClasses> {
    override fun map(input: MemberClass): StudentClasses =
        input.selectAll().map {
            StudentClasses(
                classId = it[MemberClass.classId],
                joinDate =Date()
            )
        }.first()
}