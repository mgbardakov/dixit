package site.hornsandhooves.dixit.model.lobby;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
     private UUID id;
     private String name;
     private boolean host;

     @Override
     public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;

          User user = (User) o;

          return Objects.equals(id, user.id);
     }

     @Override
     public int hashCode() {
          return id != null ? id.hashCode() : 0;
     }
}
