package com.example.automobilerestapiapp.dtos;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NamedNativeQuery(
    name = "AutomobileRepository.countAutomobilesByIsDriveableTrue",
    query = """
        SELECT sum(case when is_driveable = true then 1 else 0 end) AS driveable,sum(case when is_driveable = false then 1 else 0 end) AS notDriveable FROM automobile.automobile GROUP BY is_driveable
        """,
    resultSetMapping = "Mapping.AutomobileDriveAbilityResponse"
)
@SqlResultSetMapping(
    name = "Mapping.AutomobileDriveAbilityResponse",
    classes = @ConstructorResult(
        targetClass = AutomobileDriveAbilityResponse.class,
        columns = {
            @ColumnResult(name = "driveable"),
            @ColumnResult(name = "notDriveable")
        }
    )
)
public class AutomobileDriveAbilityResponse {
  private Integer driveable;
  private Integer notDriveable;
}