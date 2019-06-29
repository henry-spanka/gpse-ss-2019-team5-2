package gpse.team52.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

/*Candidate Entity*/
@Entity
public class Candidate {
        /**
         * Unique Id for every candidate.
         */
        @Id
        @Getter
        @GeneratedValue(generator = "uuid2")
        @GenericGenerator(name = "uuid2", strategy = "uuid2")
        @Column(name = "id", nullable = false, updatable = false,
        columnDefinition = "BINARY(16)", unique = true)
        private UUID candidateId;


        /**
         * Email of candidate.
         */
        @Getter
        @Setter
        @Column(nullable = false)
        private String email;

        /**
         * First name of candidate.
         */
        @Getter
        @Setter
        @Column(nullable = true)
        private String firstName;

        /**
         * Last name of candidate.
         */
        @Getter
        @Setter
        @Column(nullable = true)
        private String lastName;


        /**
         * Constructor for a candidate.
         * @param email Email of the candidate
         * @param firstName First name of the candidate
         * @param lastName Last name of the candidate
         */
        public Candidate(@NotNull final String email, @NotNull final String firstName,
                           @NotNull final String lastName) {
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
        }



        public String getFullName() {
            return getFirstName() + ' ' + getLastName();
        }


        @Override
        public int hashCode() {
            return Objects.hash(email, firstName, lastName);
        }
    }


