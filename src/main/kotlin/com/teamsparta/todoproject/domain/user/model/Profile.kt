package com.teamsparta.todoproject.domain.user.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Profile(
    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "introduce", nullable = false)
    var introduce: String
)