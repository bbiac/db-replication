package ac.bbi.dbreplication.member;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public void save(@RequestBody MemberSaveRequest memberSaveRequest) {
        System.out.println(memberSaveRequest);
        memberService.save(memberSaveRequest.name());
    }

    @GetMapping("/{id}")
    public MemberSaveRequest findById(@PathVariable Long id) {
        Member member = memberService.findById(id);
        return new MemberSaveRequest(member.name());
    }
}
