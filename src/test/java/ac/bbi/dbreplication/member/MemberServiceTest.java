package ac.bbi.dbreplication.member;

import static java.lang.Long.MAX_VALUE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    void 사용자를_저장한다() {
        // RoutingDataSource log: readOnly = false
        memberService.save("bbiac");
    }

    @Test
    void 사용자를_조회한다() {
        // RoutingDataSource log: readOnly = true
        assertThatThrownBy(() -> memberService.findById(MAX_VALUE))
                .isInstanceOf(NoSuchElementException.class);
    }
}
